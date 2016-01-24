package org.abendigo.csgo

data class Vector2<T : Number>(val a: T, val b: T) {

	fun toVector3() = Vector3(a, b, 0)

	operator fun get(index: Int) = if (index == 0) b else if (index == 1) a else throw IllegalArgumentException()

}