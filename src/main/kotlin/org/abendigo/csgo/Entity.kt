package org.abendigo.csgo

import org.abendigo.Addressable
import org.abendigo.cached.cached
import org.abendigo.csgo.offsets.*
import org.jire.arrowhead.get

open class Entity(override val address: Int, val id: Int, val type: EntityType) : Addressable {

	val spotted = cached<Boolean>(m_bSpotted)

	val dormant = cached<Boolean>(m_bDormant)

	val lifeState = cached<Int>(m_lifeState)
	val dead = cached { +lifeState > 0 }

	val boneMatrix = cached { csgo.get<Int>(address + m_dwBoneMatrix) }

	fun bonePosition(bone: Int): Vector {
		+boneMatrix // update in preparation
		return Vector(boneNode(bone, 0xC), boneNode(bone, 0x1C), boneNode(bone, 0x2C))
	}

	private fun boneNode(bone: Int, offset: Int): Float = csgo[boneMatrix() + ((0x30 * bone) + offset)]

	val velocity = cached {
		// TODO make a safe and easy way to do batch reading like this (to avoid native call and object allocation)
		Vector(velocity(0), velocity(4), velocity(8))
	}

	private fun velocity(offset: Int): Float = csgo[address + m_vecVelocity + offset]

	override fun hashCode() = address
	override fun equals(other: Any?) = other is Entity && address == other.address

}