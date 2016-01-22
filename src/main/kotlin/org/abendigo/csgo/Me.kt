package org.abendigo.csgo

import org.abendigo.cached.Cached
import org.abendigo.cached.cached
import org.abendigo.csgo.offsets.*

object Me : Cached<Player>({
	val address: Int = clientDLL.get(m_dwLocalPlayer)
	val index = /*client.get<Int>(address + m_dwIndex) - 1*/0 // TODO: can use me-specific index offset
	Player(address, index)
}) {

	@JvmStatic val flags = cached<Int>(this().address, m_fFlags)

	@JvmStatic val crosshairID = cached { csgo.get<Int>(this().address + m_iCrossHairID) - 1 }

	@JvmStatic val targetAddress = cached {
		val crosshairID = +crosshairID
		if (crosshairID > /*=*/ 0) clientDLL.get(m_dwEntityList + (crosshairID * ENTITY_SIZE)) else -1
	}

}