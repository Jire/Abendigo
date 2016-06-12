package org.abendigo.csgo

import org.abendigo.cached.cached
import org.abendigo.csgo.offsets.m_iShotsFired
import org.abendigo.csgo.offsets.m_iTeamNum
import org.abendigo.csgo.offsets.m_vecPunch
import org.abendigo.csgo.offsets.m_vecViewOffset

class Player(address: Int, id: Int) : Entity(address, id) {

	constructor(entity: Entity) : this(entity.address, entity.id)

	val team = cached<Int>(m_iTeamNum)

	val punch = cached { Vector(csgo[address + m_vecPunch], csgo[address + m_vecPunch + 4], 0F) }

	val shotsFired = cached<Int>(m_iShotsFired)

	val viewOrigin = cached { Vector(viewOriginNode(0), viewOriginNode(4), viewOriginNode(8)) }

	private fun viewOriginNode(offset: Int): Float = csgo[address + m_vecViewOffset + offset]

}