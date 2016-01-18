package org.abendigo.plugin.csgo

import org.abendigo.csgo.*
import org.abendigo.every
import org.abendigo.plugin.Plugin
import java.util.concurrent.TimeUnit

class AimAssistPlugin : Plugin("Aim Assist", author = "Jire", description = "Assists your aim at targets") {

	private val TARGET_BONE = 6

	@Volatile private var prevFired = 0

	override fun enable() = every(8) {
		val shotsFired = +me().shotsFired
		if (shotsFired < 1 || shotsFired < prevFired) {
			prevFired = 0
			return@every
		}
		
		// TODO check weapon (for example, weapon ID and remaining ammo)
		if (shotsFired > 1 && shotsFired >= prevFired) {
			var targetAddress = +me.targetAddress
			if (targetAddress <= 0) return@every
			var targetIndex = csgo.get<Int>(targetAddress + m_dwIndex)
			var target = enemies[targetIndex]!!
			var shots = 0

			while (!+me().dead) {
				shots = +me().shotsFired
				if (shots < prevFired) return@every
				if (+target.dead || !+target.spotted) return@every

				val myPosition = +me().position
				var position = target.bonePosition(TARGET_BONE)
				position = compensateVelocity(me(), target, position)
				val aim = calculateAngle(me(), position)
				// normalizeAngle(aim)
				val angle = clientState(8, TimeUnit.SECONDS).angle(+me().punch)
				// normalizeAngle(angle)
				//angleSmooth()
				//angleSmooth(angle, aim)
			}
		}
	}

}