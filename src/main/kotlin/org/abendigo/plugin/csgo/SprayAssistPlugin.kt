package org.abendigo.plugin.csgo

import org.abendigo.csgo.*
import org.abendigo.csgo.Engine.clientState
import org.abendigo.csgo.offsets.m_dwIndex
import org.abendigo.util.random
import org.abendigo.util.randomFloat

object SprayAssistPlugin : InGamePlugin("Spray Assist", duration = 8) {

	override val author = "Jire"
	override val description = "Assists your spraying aim at targets"

	private const val SMOOTHING_MIN = 3.5F
	private const val SMOOTHING_MAX = 7.5F

	private val TARGET_BONES = arrayOf(4, 5, 5, 6)
	private const val CHANGE_BONE_CHANCE = 18

	private var prevFired = 0
	private var target: Player? = null
	private var targetBone = newTargetBone()
	private val aim = Vector(0F, 0F, 0F)

	override fun cycle() {
		val shotsFired = +Me().shotsFired
		val weapon = +Me.weapon
		val bulletsLeft = +weapon.bullets
		if (shotsFired < 1 || shotsFired < prevFired || bulletsLeft <= 0) {
			prevFired = 0
			target = null
			return
		}

		if (target == null) {
			val targetAddress = +Me.targetAddress
			if (targetAddress <= 0) return

			val targetIndex = csgo.get<Int>(targetAddress + m_dwIndex) - 1
			try {
				target = Client.enemies[targetIndex]!!
			} catch (t: Throwable) {
				return
			}
		}

		if (+Me().dead || +target!!.dead || !+target!!.spotted) {
			target = null
			return
		}

		if (random(CHANGE_BONE_CHANCE) == 0) targetBone = newTargetBone()

		val enemyPosition = target!!.bonePosition(targetBone)
		val myPosition = +Me().position

		compensateVelocity(Me(), target!!, enemyPosition, randomFloat(SMOOTHING_MIN, SMOOTHING_MAX))

		calculateAngle(Me(), myPosition, enemyPosition, aim.reset())
		normalizeAngle(aim)

		val angle = clientState(1024).angle()
		normalizeAngle(angle)

		angleSmooth(aim, angle, randomFloat(SMOOTHING_MIN, SMOOTHING_MAX))

		prevFired = +Me().shotsFired
	}

	override fun disable() {
		target = null
	}

	private fun newTargetBone() = TARGET_BONES[random(TARGET_BONES.size)]

}