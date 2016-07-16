package org.abendigo.csgo

import org.abendigo.csgo.Engine.clientState
import org.abendigo.util.randomFloat
import java.lang.Float.NaN
import java.lang.Math.*
import java.util.concurrent.ThreadLocalRandom

const val PITCH_MIN_PUNCH = 1.88F
const val PITCH_MAX_PUNCH = 2.07F

const val YAW_MIN_PUNCH = 1.96F
const val YAW_MAX_PUNCH = 2.04F

fun normalizeAngle(angle: Vector<Float>): Vector<Float> {
	if (angle.x > 89 && angle.x <= 180) angle.x = 89F
	if (angle.x > 180) angle.x -= 360
	if (angle.x < -89) angle.x = -89F

	if (angle.y > 180) angle.y -= 360
	if (angle.y < -180) angle.y += 360

	if (angle.z != 0F) angle.z = 0F

	return angle
}

fun compensateVelocity(source: Player, target: Entity, enemyPos: Vector<Float>, smoothing: Float): Vector<Float> {
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

fun angleSmooth(dest: Vector<Float>, orig: Vector<Float>, smoothing: Float) {
	dest.x -= orig.x
	dest.y -= orig.y
	dest.z = 0F
	normalizeAngle(dest)

	dest.x = orig.x + dest.x / 100 * smoothing
	dest.y = orig.y + dest.y / 100 * smoothing
	normalizeAngle(dest)

	if (dest.x === NaN || dest.y === NaN || dest.z == NaN) return

	clientState(1024).angle(dest)
}

fun calculateAngle(player: Player, src: Vector<Float>, dst: Vector<Float>, angles: Vector<Float>): Vector<Float> {
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

fun distance(a: Vector<Float>, b: Vector<Float>) = abs(a.x - b.x) + abs(a.y - b.y) + abs(a.z - b.z)