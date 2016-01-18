package org.abendigo.csgo

import org.abendigo.updateableLazy

class Player(address: Int, id: Int) : Entity(address, id) {

	constructor(entity: Entity) : this(entity.address, entity.id)

	val team = updateableLazy { csgo.get<Int>(address + m_iTeamNum) }

	val punch = updateableLazy { Vector2<Float>(csgo.get(address + m_vecPunch), csgo.get(address + m_vecPunch + 4)) }

	val shotsFired = updateableLazy { csgo.get<Int>(address + m_iShotsFired) }

	val viewOrigin = updateableLazy { Vector3(viewOriginNode(0), viewOriginNode(4), viewOriginNode(8)) }

	private fun viewOriginNode(offset: Int): Float = csgo.get(address + m_vecViewOffset)

}