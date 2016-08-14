package org.abendigo.plugin.csgo

import org.abendigo.DEBUG
import org.abendigo.csgo.*
import org.abendigo.csgo.Client.clientDLL
import org.abendigo.csgo.Client.enemies
import org.abendigo.csgo.Engine.clientState
import org.abendigo.csgo.offsets.m_dwForceAttack
import java.lang.Math.*

object BoneTriggerPlugin : InGamePlugin("Bone Trigger", duration = 1) {

	val TARGET_BONE = Bones.HEAD
	private const val FOV = 10

	private val aim = Vector(0F, 0F, 0F)
	private var hold = false

	override fun cycle() {
		try {
			val weapon = (+Me().weapon).type!!
			if (!weapon.pistol && !weapon.shotgun && Weapons.SSG08 != weapon) return
		} catch (t: Throwable) {
			if (DEBUG) t.printStackTrace()
		}

		val myPosition = +Me().position
		val angle = clientState(1024).angle()

		var closestDelta = Int.MAX_VALUE
		for ((i, e) in enemies) {
			if (+e.dead || +e.dormant || !+e.spotted) continue

			val ePos = e.bonePosition(TARGET_BONE.id)
			val distance = distance(myPosition, ePos)

			calculateAngle(Me(), myPosition, ePos, aim.reset())
			normalizeAngle(aim)

			val pitchDiff = abs(angle.x - aim.x)
			val yawDiff = abs(angle.y - aim.y)
			val diff = sin(toRadians(pitchDiff.toDouble())) + sin(toRadians(yawDiff.toDouble()))
			val delta = abs(diff * distance)

			if (delta <= FOV && delta < closestDelta) closestDelta = delta.toInt()
		}

		if (closestDelta <= FOV && !hold) {
			clientDLL[m_dwForceAttack] = 5.toByte()
			hold = true
		} else if (hold) {
			clientDLL[m_dwForceAttack] = 4.toByte()
			hold = false
		}
	}

}