package org.abendigo.csgo

import org.abendigo.*

open class Entity(override val address: Int, val id: Int) : Addressable {

	val spotted = updateableLazy { csgo.get<Boolean>(address + m_bSpotted) }

}