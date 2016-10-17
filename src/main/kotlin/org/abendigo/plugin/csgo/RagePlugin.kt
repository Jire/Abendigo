package org.abendigo.plugin.csgo

import org.abendigo.DEBUG
import org.abendigo.csgo.*
import org.abendigo.csgo.Client.enemies
import org.abendigo.csgo.Engine.clientState
import org.abendigo.plugin.sleep
import org.abendigo.util.random
import org.abendigo.util.randomFloat
import org.jire.arrowhead.keyPressed
import org.jire.arrowhead.keyReleased
import java.lang.Math.abs

object RagePlugin : InGamePlugin(name = "Rage Aim", duration = 20) {

	override val author = "Jire"
	override val description = "Rage settings to aim at enemies through walls and smokes"

	private const val AIM_KEY = 1 /* Left click */
	private const val FORCE_AIM_KEY = 18 /* Alt */
	private const val FORCE_AIM_ENHANCEMENT = 2.5F /* set to 1.0F for no enhancement */

	private const val LOCK_FOV = 360
	private const val UNLOCK_FOV = LOCK_FOV * 2
	private const val NEVER_STICK = true

	private const val SMOOTHING_MIN = 60F
	private const val SMOOTHING_MAX = 70F

	private const val JUMP_REDUCTION = 1.0F

	private val TARGET_BONES = arrayOf(Bones.HEAD, Bones.HEAD, Bones.HEAD, Bones.HEAD)
	private const val CHANGE_BONE_CHANCE = 0

	private var target: Player? = null
	private var targetBone = newTargetBone()

	private val aim = Vector(0F, 0F, 0F)

	override fun cycle() {
		val forceAim = keyPressed(FORCE_AIM_KEY)
		if (!forceAim && keyReleased(AIM_KEY)) return

		val lockFOV = LOCK_FOV * FORCE_AIM_ENHANCEMENT
		val unlockFOV = UNLOCK_FOV * FORCE_AIM_ENHANCEMENT

		try {
			val weapon = (+Me().weapon).type!!
			if (!weapon.automatic && !weapon.pistol && !weapon.shotgun && !weapon.sniper) return
		} catch (t: Throwable) {
			if (DEBUG) t.printStackTrace()
		}

		val position = +Me().position
		val angle = clientState(1024).angle()

		if (target == null) if (!findTarget(position, angle, lockFOV)) return

		if (+Me().dead || +target!!.dead || +target!!.dormant) {
			target = null
			sleep(random(200, 400)) // prevent quick flick
			return
		}

		aimAt(position, angle, target!!, unlockFOV)
	}

	private fun newTargetBone() = TARGET_BONES[random(TARGET_BONES.size)]

	private fun findTarget(position: Vector, angle: Vector, lockFOV: Float): Boolean {
		var closestDelta = Int.MAX_VALUE
		var closetPlayer: Player? = null
		for ((i, e) in enemies) {
			if (+Me().dead) return false
			if (+e.dead || +e.dormant) continue

			val ePos = e.bonePosition(Bones.HEAD.id)
			val distance = distance(position, ePos)

			calculateAngle(Me(), position, ePos, aim.reset())
			normalizeAngle(aim)

			val yawDiff = abs(angle.y - aim.y)
			val delta = abs(Math.sin(Math.toRadians(yawDiff.toDouble())) * distance)

			if (delta <= lockFOV && delta < closestDelta) {
				closestDelta = delta.toInt()
				closetPlayer = e
			}
		}

		if (closetPlayer != null) {
			target = closetPlayer
			return true
		}

		return false
	}

	private fun aimAt(position: Vector, angle: Vector, target: Player, unlockFOV: Float) {
		var smoothingMin = SMOOTHING_MIN * FORCE_AIM_ENHANCEMENT
		var smoothingMax = SMOOTHING_MAX * FORCE_AIM_ENHANCEMENT
		if (+target.flags and 1 == 0 || +Me().flags and 1 == 0) {
			smoothingMin *= JUMP_REDUCTION
			smoothingMax *= JUMP_REDUCTION
		}

		if (random(CHANGE_BONE_CHANCE) == 0) targetBone = newTargetBone()

		val enemyPosition = target.bonePosition(targetBone.id)

		val smoothing = randomFloat(smoothingMin, smoothingMax)

		compensateVelocity(Me(), target, enemyPosition, smoothing)

		calculateAngle(Me(), position, enemyPosition, aim.reset())
		normalizeAngle(aim)

		normalizeAngle(angle)

		val distance = distance(position, enemyPosition)
		val yawDelta = abs(angle.y - aim.y)
		val deltaFOV = abs(Math.sin(Math.toRadians(yawDelta.toDouble())) * distance)

		if (deltaFOV >= unlockFOV) RagePlugin.target = null
		else angleSmooth(aim, angle, smoothing)

		if (NEVER_STICK) RagePlugin.target = null
	}

}