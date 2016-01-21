package org.abendigo.csgo

import org.abendigo.cached.cached

class Player(address: Int, id: Int) : Entity(address, id) {

	constructor(entity: Entity) : this(entity.address, entity.id)

	val team = cached { csgo.get<Int>(address + m_iTeamNum) }

	val punch = cached { Vector2<Float>(csgo.get(address + m_vecPunch), csgo.get(address + m_vecPunch + 4)) }

	val shotsFired = cached { csgo.get<Int>(address + m_iShotsFired) }

	val viewOrigin = cached { Vector3(viewOriginNode(0), viewOriginNode(4), viewOriginNode(8)) }

	private fun viewOriginNode(offset: Int): Float = csgo.get(address + m_vecViewOffset)

}