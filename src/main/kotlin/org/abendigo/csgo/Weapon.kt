package org.abendigo.csgo

import org.abendigo.Addressable
import org.abendigo.cached.cached
import org.abendigo.csgo.offsets.*
import org.jire.arrowhead.get

open class Weapon(override val address: Int, val index: Int, val id: Int, val base: Int) : Addressable {

	val bullets = cached { if (base > 0) csgo[base + m_iClip1] else Int.MAX_VALUE }

	val nextPrimaryAttack = cached { if (base > 0) csgo[base + m_flNextPrimaryAttack] else Float.MAX_VALUE }

	val type by lazy(LazyThreadSafetyMode.NONE) { Weapons.byID(id) }

	fun canFire(): Boolean {
		val nextAttack = +nextPrimaryAttack
		return nextAttack <= 0 || nextAttack < Me.time()
	}

}