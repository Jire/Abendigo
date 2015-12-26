package org.abendigo.csgo

import java.util.concurrent.ThreadLocalRandom

const val SMOOTHING = 9F
const val SMOOTHING_FACTOR = 40 / SMOOTHING

fun compensateVelocity(source: Player, target: Entity, pos: Entity.Position): Entity.Velocity {
	val sv = +source.velocity
	val tv = +target.velocity
	var x = pos.x + (tv.x / 100) * SMOOTHING_FACTOR
	var y = pos.y + (tv.y / 100) * SMOOTHING_FACTOR
	var z = pos.z + (tv.z / 100) * SMOOTHING_FACTOR
	x -= (sv.x / 100) * SMOOTHING_FACTOR
	y -= (sv.y / 100) * SMOOTHING_FACTOR
	z -= (sv.z / 100) * SMOOTHING_FACTOR
	return Entity.Velocity(x, y, z)
}

fun rndFloat(min: Float, max: Float) = min + ThreadLocalRandom.current().nextFloat() * (max - min)