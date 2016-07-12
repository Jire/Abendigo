package org.abendigo.csgo

import org.abendigo.cached.cached
import org.abendigo.csgo.offsets.*

class Player(address: Int, id: Int, type: EntityType) : Entity(address, id, type) {

	constructor(entity: Entity) : this(entity.address, entity.id, entity.type)

	val team = cached<Int>(m_iTeamNum)

	val punch = cached { Vector(csgo[address + m_vecPunch], csgo[address + m_vecPunch + 4], 0F) }

	val shotsFired = cached<Int>(m_iShotsFired)

	val viewOffset = cached<Float> { csgo[address + m_vecViewOffset] }

	val position = cached {
		val zOffset: Float = csgo[address + m_vecViewOffset + 8]
		Vector(posNode(0), posNode(4), posNode(8) + zOffset)
	}

	private fun posNode(offset: Int): Float = csgo[address + m_vecOrigin + offset]

}