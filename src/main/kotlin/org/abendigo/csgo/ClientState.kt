package org.abendigo.csgo

import org.abendigo.Addressable
import org.abendigo.csgo.offsets.m_dwInGame
import org.abendigo.csgo.offsets.m_dwViewAngles

class ClientState(override val address: Int) : Addressable {

	fun gameState() = gameState(csgo[address + m_dwInGame])

	fun angle() = Vector(angle(0), angle(4), angle(8))

	private fun angle(offset: Int): Float = csgo[address + m_dwViewAngles + offset]

	fun angle(angle: Vector<Float>) {
		csgo[address + m_dwViewAngles] = angle.x // pitch (up and down)
		csgo[address + m_dwViewAngles + 4] = angle.y // yaw (side to side)
		// csgo[address + m_dwViewAngles + 8] = 0F // roll (twist)
		// never write roll because it risks ban
	}

}