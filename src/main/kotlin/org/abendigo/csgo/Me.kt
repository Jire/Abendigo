package org.abendigo.csgo

import org.abendigo.cached.Cached
import org.abendigo.cached.cached
import org.abendigo.csgo.offsets.*

object Me : Cached<Player>({
	val address: Int = clientDLL.get(m_dwLocalPlayer)
	val index = csgo.get<Int>(address + m_dwIndex) - 1
	Player(address, index)
}) {

	@JvmStatic val flags = cached<Int>(this().address, m_fFlags)

	@JvmStatic val crosshairID = cached { csgo.get<Int>(this().address + m_iCrossHairID) - 1 }

	@JvmStatic val targetAddress = cached {
		val crosshairID = +crosshairID
		if (crosshairID > /*=*/ 0) clientDLL.get(m_dwEntityList + (crosshairID * ENTITY_SIZE)) else -1
	}

	@JvmStatic fun punch(): Vector2<Float> {
		val a = csgo.get<Float>(this().address + m_vecPunch)
		val b = csgo.get<Float>(this().address + m_vecPunch + 4)
		val vector = Vector2(a, b)
		println("Punch $vector")
		return vector
	}

}