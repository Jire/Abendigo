package org.abendigo.csgo

import org.abendigo.DEBUG
import org.abendigo.cached.cached
import org.abendigo.csgo.Client.clientDLL
import org.abendigo.csgo.offsets.*
import org.jire.arrowhead.get

class Player(address: Int, id: Int, type: EntityType) : Entity(address, id, type) {

	constructor(entity: Entity) : this(entity.address, entity.id, entity.type)

	val team = cached<Int>(m_iTeamNum)

	val punch = cached { Vector(csgo[address + m_vecPunch], csgo[address + m_vecPunch + 4], 0F) }

	val shotsFired = cached<Int>(m_iShotsFired)

	val flags = cached<Int>(address, m_fFlags)

	val viewOffset = cached<Float> { csgo[address + m_vecViewOffset] }

	val position = cached {
		val zOffset: Float = csgo[address + m_vecViewOffset + 8]
		Vector(posNode(0), posNode(4), posNode(8) + zOffset)
	}

	private fun posNode(offset: Int): Float = csgo[address + m_vecOrigin + offset]

	val weapon = cached {
		val address: Int = csgo[address + m_hActiveWeapon]
		val index = address and 0xFFF
		val base: Int = clientDLL[m_dwEntityList + (index - 1) * ENTITY_SIZE]
		var id = 42
		if (base > 0) id = csgo[base + m_iWeaponID]
		Weapon(address, index, id, base)
	}

	fun hasWeapon(weapon: Weapons): Boolean {
		for (i in 1..9) try {
			var currentWeaponIndex: Int = csgo[address + m_hMyWeapons + ((i - 1) * 0x4)]
			currentWeaponIndex = currentWeaponIndex and 0xFFF
			val weaponAddress: Int = clientDLL[m_dwEntityList + (currentWeaponIndex - 1) * 0x10]
			if (weaponAddress <= 0) return false
			val weaponID: Int = csgo[weaponAddress + m_iItemDefinitionIndex]
			if (weapon.id == weaponID) return true
		} catch (t: Throwable) {
			if (DEBUG) t.printStackTrace()
		}
		return false
	}

}