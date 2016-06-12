package org.abendigo.csgo

import org.abendigo.Addressable
import org.abendigo.cached.cached
import org.abendigo.csgo.offsets.m_iClip1

open class Weapon(override val address: Int, val index: Int, val id: Int, val base: Int) : Addressable {

	val bullets = cached<Int> { csgo[base + m_iClip1] }

}