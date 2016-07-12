package org.abendigo.csgo

import org.abendigo.cached.Cached
import org.abendigo.cached.cached
import org.abendigo.csgo.offsets.*
import org.abendigo.csgo.Client.clientDLL

object Me : Cached<Player>({
	val address: Int = clientDLL[m_dwLocalPlayer]
	val index = csgo.get<Int>(address + m_dwIndex) - 1
	Player(address, index, EntityType.CCSPlayer)
}) {

	@JvmStatic val flags = cached<Int>(this().address, m_fFlags)

	@JvmStatic val crosshairID = cached { csgo.get<Int>(this().address + m_iCrossHairID) - 1 }

	@JvmStatic val targetAddress = cached {
		val crosshairID = +crosshairID
		if (crosshairID > /*=*/ 0) clientDLL[m_dwEntityList + (crosshairID * ENTITY_SIZE)] else -1
	}

	@JvmStatic fun punch() = Vector(csgo[this().address + m_vecPunch], csgo[this().address + m_vecPunch + 4], 0F)

	val weapon = cached {
		val address: Int = csgo[this().address + m_hActiveWeapon]
		val index = address and 0xFFF
		val base: Int = clientDLL[m_dwEntityList + (index - 1) * ENTITY_SIZE]
		var id = 42
		if (base > 0) id = csgo[base + m_iWeaponID]
		Weapon(address, index, id, base)
	}

	val inScope = cached {
		val scoped: Byte = csgo[this().address + m_bIsScoped]
		scoped > 0
	}

	const val TICK_RATIO = 1F / 64F

	fun time() = csgo.get<Int>(this().address + m_nTickBase) * TICK_RATIO

}