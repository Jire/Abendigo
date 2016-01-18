package org.abendigo.csgo

data class Vector3<T : Number>(val a: T, val b: T, val c: T) {

	fun toVector2() = Vector2(a, b)

	operator fun get(index: Int) = if (index == 0) a else if (index == 1) b else c

}