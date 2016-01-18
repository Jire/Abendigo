package org.abendigo.csgo

import org.abendigo.Addressable

class ClientState(override val address: Int) : Addressable {

	//val angle = updateableLazy { csgo.get(address + m_dwViewAngles) }

	fun angle(angle: Vector2<Float>) = csgo.set(address + m_dwViewAngles, angle[0])

}