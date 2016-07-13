package org.abendigo.plugin.csgo

import org.abendigo.DEBUG
import org.abendigo.csgo.Engine.clientState
import org.abendigo.csgo.Me
import org.abendigo.csgo.Vector
import org.abendigo.csgo.normalizeAngle
import org.abendigo.util.randomFloat
import org.jire.kotmem.Keys

object RCSPlugin : InGamePlugin("RCS", duration = 32) {

	// 0F = no correction, 2F = 100% correction
	private const val RANDOM_MIN = 1.86F
	private const val RANDOM_MAX = 1.97F

	private var prevFired = 0
	private val lastPunch = FloatArray(2)

	override fun cycle() {
		val shotsFired = +Me().shotsFired

		val weapon = +Me.weapon
		try {
			if (!weapon.type!!.automatic) {
				reset()
				return
			}
		} catch (t: Throwable) {
			if (DEBUG) t.printStackTrace()
		}

		val bulletsLeft = +weapon.bullets
		if (shotsFired <= 2 || shotsFired < prevFired || bulletsLeft <= 0 || +Me().dead) {
			if (!Keys[1]) { // prevent aim flick down cheaphax
				reset()
				return
			}
		}

		val punch = +Me().punch

		punch.x *= randomFloat(RANDOM_MIN, RANDOM_MAX)
		punch.y *= randomFloat(RANDOM_MIN, RANDOM_MAX)
		punch.z = 0F
		normalizeAngle(punch)

		var view = clientState(1024).angle()
		if (view.x == 0F || view.y == 0F || view.z == 0F) view = clientState(1024).angle()

		val newView = Vector(punch.x, punch.y, punch.z)

		newView.x -= lastPunch[0]
		newView.y -= lastPunch[1]
		newView.z = 0F
		normalizeAngle(newView)

		view.x -= newView.x
		view.y -= newView.y
		view.z = 0F
		normalizeAngle(view)

		clientState(1024).angle(view)

		lastPunch[0] = punch.x
		lastPunch[1] = punch.y

		prevFired = shotsFired
	}

	private fun reset() {
		prevFired = 0
		lastPunch[0] = 0F
		lastPunch[1] = 0F
	}

	override fun disable() {
		reset()
	}

}