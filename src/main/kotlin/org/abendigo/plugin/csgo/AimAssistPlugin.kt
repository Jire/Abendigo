package org.abendigo.plugin.csgo

import org.abendigo.csgo.*
import org.abendigo.csgo.Engine.clientState
import org.abendigo.csgo.offsets.m_dwIndex
import java.util.concurrent.TimeUnit

object AimAssistPlugin : InGamePlugin("Aim Assist", author = "Jire", description = "Assists your aim at targets", duration = 8) {

	private val TARGET_BONE = 6

	@Volatile private var prevFired = 0

	override fun cycle() {
		val shotsFired = +Me().shotsFired
		if (shotsFired < 1 || shotsFired < prevFired) {
			prevFired = 0
			return
		}

		// TODO check weapon (for example, weapon ID and remaining ammo)
		if (shotsFired > 1 && shotsFired >= prevFired) {
			var targetAddress = +Me.targetAddress
			if (targetAddress <= 0) return
			var targetIndex = csgo.get<Int>(targetAddress + m_dwIndex) - 1
			var target = try {
				Client.enemies[targetIndex]!!
			} catch (t: Throwable) {
				return
			}
			var shots = 0

			/*while*/ if (!+Me().dead) {
				shots = +Me().shotsFired
				if (shots < prevFired) return
				if (+target.dead || !+target.spotted) return

				val myPosition = +Me().position
				var position = target.bonePosition(TARGET_BONE)
				position = compensateVelocity(Me(), target, position)
				val aim = clampAngle(calculateAngle(Me(), position))
				val angle = clampAngle(clientState(4, TimeUnit.SECONDS).angle())
				//clientState(4, TimeUnit.SECONDS).angle(+Me().punch)
				// normalizeAngle(angle)
				//angleSmooth()
				angleSmooth(angle, aim)
			}
		}
	}

}