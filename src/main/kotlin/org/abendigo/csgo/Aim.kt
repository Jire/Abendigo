package org.abendigo.csgo

import org.abendigo.csgo.Engine.clientState
import org.abendigo.util.randomFloat
import java.lang.Float.NaN
import java.lang.Math.atan
import java.lang.Math.sqrt
import java.util.concurrent.ThreadLocalRandom

const val PITCH_MIN_PUNCH = 1F
const val PITCH_MAX_PUNCH = 3F

const val YAW_MIN_PUNCH = 1.7F
const val YAW_MAX_PUNCH = 2.5F

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

	val smoothingFactor = 40F / smoothing
	enemyPos.x += (enemyVelocity.x / 100F) * smoothingFactor
	enemyPos.y += (enemyVelocity.y / 100F) * smoothingFactor
	enemyPos.z += (enemyVelocity.z / 100F) * smoothingFactor
	enemyPos.x -= (myVelocity.x / 100F) * smoothingFactor
	enemyPos.y -= (myVelocity.y / 100F) * smoothingFactor
	enemyPos.z -= (myVelocity.z / 100F) * smoothingFactor

	return enemyPos
}

fun angleSmooth(dest: Vector<Float>, orig: Vector<Float>, smoothing: Float) {
	dest.x -= orig.x
	dest.y -= orig.y
	dest.z = 0F
	normalizeAngle(dest)

	dest.x = orig.x + dest.x / 100F * smoothing
	dest.y = orig.y + dest.y / 100F * smoothing
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
	val dZ = src.z + (+player.viewOrigin).z - dst.z

	val hyp = sqrt(dX.toDouble() * dX + dY.toDouble() * dY)

	angles.x = (atan(dZ.toDouble() / hyp) * (180 / Math.PI) - myPunch.x * pitchReduction).toFloat()
	angles.y = (atan(dY.toDouble() / dX) * (180 / Math.PI) - myPunch.y * yawReduction).toFloat()
	angles.z = 0F
	if (dX >= 0) angles.y += 180

	return angles
}