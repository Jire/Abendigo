package org.abendigo.csgo

import org.abendigo.updateableLazy

class Player(address: Int, id: Int) : Entity(address, id) {

	constructor(entity: Entity) : this(entity.address, entity.id)

	val team = updateableLazy { csgo.get<Int>(address + m_iTeamNum) }

}