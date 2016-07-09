package org.abendigo.plugin.csgo

import org.abendigo.csgo.Engine.clientState
import org.abendigo.csgo.Me
import org.abendigo.csgo.Vector
import org.abendigo.csgo.normalizeAngle

object RCSPlugin : InGamePlugin("RCS", duration = 16) {

	private var prevFired = 0
	private val lastPunch = FloatArray(2)

	override fun cycle() {
		val shotsFired = +Me().shotsFired
		val weapon = +Me.weapon
		val bulletsLeft = +weapon.bullets
		if (shotsFired < 1 || shotsFired < prevFired || bulletsLeft <= 0 || +Me().dead) {
			prevFired = 0
			lastPunch[0] = 0F
			lastPunch[1] = 0F
			return
		}

		val angle = clientState(1024).angle()

		val punch = +Me().punch

		val vax = punch.x - lastPunch[0]
		val vay = punch.y - lastPunch[1]

		angle.x -= vax * 2F
		angle.y -= vay * 2F

		clientState(1024).angle(normalizeAngle(angle))

		lastPunch[0] = punch.x
		lastPunch[1] = punch.y

		prevFired = shotsFired
	}

}