package org.abendigo.csgo

import org.abendigo.*
import org.abendigo.csgo.Client.clientDLL
import org.abendigo.csgo.Engine.clientState
import org.abendigo.csgo.offsets.m_bSendPacket
import org.abendigo.csgo.offsets.m_dwInGame
import org.abendigo.csgo.offsets.m_dwInput
import org.abendigo.csgo.offsets.m_dwViewAngles
import org.abendigo.util.mouseMove
import org.jire.arrowhead.get

class ClientState(override val address: Int) : Addressable {

	fun gameState() = gameState(csgo[address + m_dwInGame])

	fun angle() = Vector(angle(0), angle(4), angle(8))

	private fun angle(offset: Int): Float = csgo[address + m_dwViewAngles + offset]

	fun angle(angle: Vector, currentAngle: Vector = angle(), sendInput: Boolean = TRUE_MOUSE_MOVEMENT) {
		if (angle.z != 0F || angle.x < -89 || angle.x > 180 || angle.y < -180 || angle.y > 180
				|| angle.x.isNaN() || angle.y.isNaN() || angle.z.isNaN()) return

		if (sendInput) {
			val delta = Vector(currentAngle.y - angle.y, currentAngle.x - angle.x, 0F)

			val dx = Math.round(delta.x / (IN_GAME_SENS * IN_GAME_PITCH))
			val dy = Math.round(-delta.y / (IN_GAME_SENS * IN_GAME_YAW))

			mouseMove(dx.toInt(), dy.toInt())
		} else {
			csgo[address + m_dwViewAngles] = angle.x // pitch (up and down)
			csgo[address + m_dwViewAngles + 4] = angle.y // yaw (side to side)
			// csgo[address + m_dwViewAngles + 8] = angle.z // roll (twist)
		}
	}

	private fun silentAngle(angle: Vector) {
		try {
			val weapon = +Me().weapon
			if (!weapon.canFire()) return

			val userCMDBase: Int = clientDLL[m_dwInput + 0xEC]
			var desiredTick: Int = csgo[clientState(1024).address + 0x4CB8]

			engineDLL[m_bSendPacket] = false

			for (i in 0..8) {
				desiredTick += 1
				val userCMD = userCMDBase + ((desiredTick % 150) * 0x64) // future usercmd

				var previousAngle = Vector(0F, 0F, 0F)


				var tick = 0
				while (tick != desiredTick) {
					previousAngle = angle()
					tick = csgo[userCMD + 0x4]
				}

				for (i2 in 0..20) { // make sure we hit the timing at one point or another lol
					csgo[userCMD + 0xC] = angle.x
					csgo[userCMD + 0xC + 4] = angle.y
				}

				angle(previousAngle)
			}

			engineDLL[m_bSendPacket] = true
		} catch (t: Throwable) {
			if (DEBUG) t.printStackTrace()
		}
	}

}