package org.abendigo.csgo

import org.abendigo.Addressable
import org.abendigo.cached.cached

open class Entity(override val address: Int, val id: Int) : Addressable {

	val spotted = cached<Boolean>(m_bSpotted)

	val dormant = cached<Boolean>(m_bDormant)

	val lifeState = cached<Int>(m_lifeState)
	val dead = cached { +lifeState > 0 }

	val boneMatrix = cached<Int>(m_dwBoneMatrix)

	fun bonePosition(bone: Int): Vector3<Float> {
		+boneMatrix // update in preparation
		return Vector3(boneNode(bone, 0x0C), boneNode(bone, 0x1C), boneNode(bone, 0x2C))
	}

	private fun boneNode(bone: Int, offset: Int): Float = csgo.get(boneMatrix() + 0x30 * bone + offset)

	val position = cached { Vector3(posNode(0), posNode(4), posNode(8)) }

	private fun posNode(offset: Int): Float = csgo.get(address + m_vecOrigin + offset)

	val velocity = cached {
		// TODO make a safe and easy way to do batch reading like this (to avoid native call)
		val x = csgo.get<Float>(address + m_vecVelocity)
		val y = csgo.get<Float>(address + m_vecVelocity + 4)
		val z = csgo.get<Float>(address + m_vecVelocity + 8)
		Vector3(x, y, z)
	}

	override fun hashCode() = address
	override fun equals(other: Any?) = other is Entity && address == other.address

}