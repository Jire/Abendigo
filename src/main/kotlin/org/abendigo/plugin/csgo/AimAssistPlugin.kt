package org.abendigo.plugin.csgo

import org.abendigo.csgo.*
import org.abendigo.csgo.Client.enemies
import org.abendigo.csgo.Engine.clientState
import org.abendigo.csgo.offsets.m_dwIndex
import org.abendigo.plugin.Plugin
import org.abendigo.plugin.every
import java.util.concurrent.TimeUnit

object AimAssistPlugin : Plugin("Aim Assist", author = "Jire", description = "Assists your aim at targets") {

	private val TARGET_BONE = 6

	@Volatile private var prevFired = 0

	override fun enable() = every(8) {
		val shotsFired = +Me().shotsFired
		if (shotsFired < 1 || shotsFired < prevFired) {
			prevFired = 0
			return@every
		}

		// TODO check weapon (for example, weapon ID and remaining ammo)
		if (shotsFired > 1 && shotsFired >= prevFired) {
			var targetAddress = +Me.targetAddress
			if (targetAddress <= 0) return@every
			var targetIndex = csgo.get<Int>(targetAddress + m_dwIndex)
			var target = enemies[targetIndex]!!
			var shots = 0

			while (!+Me().dead) {
				shots = +Me().shotsFired
				if (shots < prevFired) return@every
				if (+target.dead || !+target.spotted) return@every

				val myPosition = +Me().position
				var position = target.bonePosition(TARGET_BONE)
				position = compensateVelocity(Me(), target, position)
				val aim = calculateAngle(Me(), position)
				// normalizeAngle(aim)
				val angle = clientState(8, TimeUnit.SECONDS).angle(+Me().punch)
				// normalizeAngle(angle)
				//angleSmooth()
				//angleSmooth(angle, aim)
			}
		}
	}

}