package org.abendigo.csgo

data class Vector<T : Number>(var x: T, var y: T, var z: T) {

	operator fun get(index: Int) = if (index == 0) x else if (index == 1) y else z

	fun reset() = apply {
		x = 0 as T
		y = 0 as T
		z = 0 as T
	}

}