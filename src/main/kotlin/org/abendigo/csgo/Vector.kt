package org.abendigo.csgo

data class Vector(var x: Float, var y: Float, var z: Float) {

	fun reset() = apply {
		x = 0F
		y = 0F
		z = 0F
	}

}