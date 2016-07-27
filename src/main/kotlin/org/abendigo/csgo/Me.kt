package org.abendigo.csgo

import org.abendigo.cached.Cached
import org.abendigo.cached.cached
import org.abendigo.csgo.offsets.*
import org.abendigo.csgo.Client.clientDLL
import org.jire.arrowhead.get

object Me : Cached<Player>({
	val address: Int = clientDLL[m_dwLocalPlayer]
	val index = csgo.get<Int>(address + m_dwIndex) - 1
	Player(address, index, EntityType.CCSPlayer)
}) {

	@JvmStatic val crosshairID = cached { csgo.get<Int>(this().address + m_iCrossHairID) - 1 }

	@JvmStatic val targetAddress = cached {
		val crosshairID = +crosshairID
		if (crosshairID > /*=*/ 0) clientDLL[m_dwEntityList + (crosshairID * ENTITY_SIZE)] else -1
	}

	@JvmStatic fun punch() = Vector(csgo[this().address + m_vecPunch], csgo[this().address + m_vecPunch + 4], 0F)

	val inScope = cached {
		val scoped: Byte = csgo[this().address + m_bIsScoped]
		scoped > 0
	}

	const val TICK_RATIO = 1F / 64F

	fun time() = csgo.get<Int>(this().address + m_nTickBase) * TICK_RATIO

}