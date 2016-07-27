package org.abendigo.csgo

import org.abendigo.Addressable
import org.abendigo.DEBUG
import org.abendigo.csgo.Client.clientDLL
import org.abendigo.csgo.Engine.clientState
import org.abendigo.csgo.offsets.m_bSendPacket
import org.abendigo.csgo.offsets.m_dwInGame
import org.abendigo.csgo.offsets.m_dwInput
import org.abendigo.csgo.offsets.m_dwViewAngles
import org.jire.arrowhead.get

class ClientState(override val address: Int) : Addressable {

	fun gameState() = gameState(csgo[address + m_dwInGame])

	fun angle() = Vector(angle(0), angle(4), angle(8))

	private fun angle(offset: Int): Float = csgo[address + m_dwViewAngles + offset]

	fun angle(angle: Vector<Float>) {
		// ignore these angle requests as often they cause a flick
		if (angle.x < -89 || angle.x > 180 || angle.y < -180 || angle.y > 180) return

		csgo[address + m_dwViewAngles] = angle.x // pitch (up and down)
		csgo[address + m_dwViewAngles + 4] = angle.y // yaw (side to side)
		// csgo[address + m_dwViewAngles + 8] = angle.z // roll (twist) p.s. don't use because it can cause untrusted
	}

	private fun silentAngle(angle: Vector<Float>) {
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

				for (i in 0..20) { // make sure we hit the timing at one point or another lol
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