package org.abendigo.csgo

import org.abendigo.csgo.Engine.clientState
import java.util.concurrent.ThreadLocalRandom

const val SMOOTHING = 9F
const val SMOOTHING_FACTOR = 40 / SMOOTHING

const val PITCH_MIN_PUNCH = 1.7F
const val PITCH_MAX_PUNCH = 2.5F

const val YAW_MIN_PUNCH = 1.7F
const val YAW_MAX_PUNCH = 2.5F

fun compensateVelocity(source: Player, target: Entity, pos: Vector3<Float>): Vector3<Float> {
	val sv = +source.velocity
	val tv = +target.velocity
	var x = pos[0] + (tv[0] / 100) * SMOOTHING_FACTOR
	var y = pos[1] + (tv[1] / 100) * SMOOTHING_FACTOR
	var z = pos[2] + (tv[2] / 100) * SMOOTHING_FACTOR
	x -= (sv[0] / 100) * SMOOTHING_FACTOR
	y -= (sv[1] / 100) * SMOOTHING_FACTOR
	z -= (sv[2] / 100) * SMOOTHING_FACTOR
	return Vector3(x, y, z)
}

fun rndFloat(min: Float, max: Float) = min + ThreadLocalRandom.current().nextFloat() * (max - min)

fun angleSmooth(source: Vector2<Float>, target: Vector2<Float>) {
	var pitch = target[0] - source[0]
	var yaw = target[1] - source[1]
	pitch = source[0] + pitch / 100F * SMOOTHING
	yaw = source[1] + yaw / 100F * SMOOTHING

	if (pitch != Float.NaN && yaw != Float.NaN)
		(+clientState).angle(Vector2(pitch, yaw))
}

fun calculateAngle(source: Player, target: Vector3<Float>): Vector2<Float> {
	val sourcePunch = +source.position
	val pitchReduction = rndFloat(PITCH_MIN_PUNCH, PITCH_MAX_PUNCH)
	val yawReduction = rndFloat(YAW_MIN_PUNCH, YAW_MAX_PUNCH)
	val deltaPitch = sourcePunch[0] - target[0]
	val deltaYaw = sourcePunch[1] - target[1]
	val deltaRoll = (sourcePunch[2] + source.viewOrigin()[2]) - target[2]
	val hyp = Math.sqrt(deltaPitch.toDouble() * deltaPitch + deltaYaw * deltaYaw)
	val pitch = Math.atan(deltaRoll / hyp) * (180 / Math.PI) - sourcePunch[0] * pitchReduction
	var yaw = Math.atan(deltaYaw.toDouble() / deltaPitch) * (180 / Math.PI) - sourcePunch[1] * yawReduction
	//val roll = 0F
	if (deltaPitch >= 0.0) yaw += 180.0
	return Vector2(pitch.toFloat(), yaw.toFloat()/*, roll*/)
}

fun clampAngle(angle: Vector2<Float>): Vector2<Float> {
	var a = angle.a
	var b = angle.b

	if (!a.isFinite()) a = 0F
	if (!b.isFinite()) b = 0F

	while (b < -180F) b += 360F
	while (b > 180F) b -= 360F

	if (a > 89F) a = 89F
	if (a < -89F) a = -89F

	return Vector2(a, b)
}