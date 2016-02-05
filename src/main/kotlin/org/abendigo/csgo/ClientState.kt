package org.abendigo.csgo

import org.abendigo.Addressable
import org.abendigo.csgo.offsets.m_dwViewAngles

class ClientState(override val address: Int) : Addressable {

	fun angle(): Vector2<Float> {
		val a = csgo.get<Float>(address + m_dwViewAngles)
		val b = csgo.get<Float>(address + m_dwViewAngles + 4)
		val vector = Vector2(a, b)
		println("Angle $vector")
		return vector
	}

	/*val angle = cached<Vector2<Float>> {
		val a = csgo.get<Float>(address + m_dwViewAngles)
		val b = csgo.get<Float>(address + m_dwViewAngles + 4)
		Vector2(a, b)
	}*/

	fun angle(angle: Vector2<Float>) {
		csgo[address + m_dwViewAngles] = angle[0]
		csgo[address + m_dwViewAngles + 4] = angle[1]
	}

}