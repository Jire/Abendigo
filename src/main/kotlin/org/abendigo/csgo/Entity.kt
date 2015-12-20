package org.abendigo.csgo

import org.abendigo.Addressable
import org.abendigo.updateableLazy

open class Entity(override val address: Int, val id: Int) : Addressable {

	val spotted = updateableLazy { csgo.get<Boolean>(address + m_bSpotted) }
	val dormant = updateableLazy { csgo.get<Boolean>(address + m_bDormant) }
	val lifeState = updateableLazy { csgo.get<Int>(address + m_lifeState) }

}