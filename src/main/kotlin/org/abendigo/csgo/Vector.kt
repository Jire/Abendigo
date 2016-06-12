package org.abendigo.csgo

data class Vector<T : Number>(var x: T, var y: T, var z: T) {

	fun reset() = apply {
		x = 0 as T
		y = 0 as T
		z = 0 as T
	}

}