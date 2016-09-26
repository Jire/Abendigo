package org.abendigo.csgo

import org.abendigo.csgo.Engine.clientState
import org.abendigo.util.randomFloat
import java.lang.Float.NaN
import java.lang.Math.*
import java.util.concurrent.ThreadLocalRandom

const val PITCH_MIN_PUNCH = 1.96F
const val PITCH_MAX_PUNCH = 2.07F

const val YAW_MIN_PUNCH = 1.97F
const val YAW_MAX_PUNCH = 2.02F

fun Vector.normalize() = apply {
	if (x != x) x = 0F
	if (y != y) y = 0F

	if (x > 89) x = 89F
	if (x < -89) x = -89F

	while (y > 180) y -= 360
	while (y <= -180) y += 360

	if (y > 180) y = 180F
	if (y < -180F) y = -180F

	z = 0F
}

fun normalizeAngle(angle: Vector) = angle.normalize()

fun compensateVelocity(source: Player, target: Entity, enemyPos: Vector, smoothing: Float): Vector {
	val myVelocity = +source.velocity
	val enemyVelocity = +target.velocity

	val smoothingFactor = 40 / smoothing
	enemyPos.x += (enemyVelocity.x / 100) * smoothingFactor
	enemyPos.y += (enemyVelocity.y / 100) * smoothingFactor
	enemyPos.z += (enemyVelocity.z / 100) * smoothingFactor
	enemyPos.x -= (myVelocity.x / 100) * smoothingFactor
	enemyPos.y -= (myVelocity.y / 100) * smoothingFactor
	enemyPos.z -= (myVelocity.z / 100) * smoothingFactor

	return enemyPos
}

fun angleSmooth(dest: Vector, orig: Vector, smoothing: Float) {
	dest.x -= orig.x
	dest.y -= orig.y
	dest.z = 0F
	normalizeAngle(dest)

	dest.x = orig.x + dest.x / 100 * smoothing
	dest.y = orig.y + dest.y / 100 * smoothing

	normalizeAngle(dest)

	clientState(1024).angle(dest, orig)
}

fun calculateAngle(player: Player, src: Vector, dst: Vector, angles: Vector): Vector {
	val pitchReduction = randomFloat(PITCH_MIN_PUNCH, PITCH_MAX_PUNCH)
	val yawReduction = randomFloat(YAW_MIN_PUNCH, YAW_MAX_PUNCH)

	val myPunch = +player.punch

	val dX = src.x - dst.x
	val dY = src.y - dst.y
	val dZ = src.z + +player.viewOffset - dst.z

	val hyp = sqrt(dX.toDouble() * dX + dY.toDouble() * dY)

	angles.x = (atan(dZ / hyp) * (180 / PI) - myPunch.x * pitchReduction).toFloat()
	angles.y = (atan(dY.toDouble() / dX) * (180 / PI) - myPunch.y * yawReduction).toFloat()
	angles.z = 0F
	if (dX >= 0) angles.y += 180

	return angles
}

fun distance(a: Vector, b: Vector) = abs(a.x - b.x) + abs(a.y - b.y) + abs(a.z - b.z)