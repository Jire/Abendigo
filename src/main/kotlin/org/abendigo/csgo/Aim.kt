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
	if (angle[0] > 89 && angle[0] <= 180) angle.x = 89F
	if (angle[0] > 180) angle.x -= 360
	if (angle[0] < -89) angle.x = -89F

	if (angle[1] > 180) angle.y -= 360
	if (angle[1] < -180) angle.y += 360

	if (angle.z != 0F) angle.z = 0F

	return angle
}

fun compensateVelocity(source: Player, target: Entity, enemyPos: Vector<Float>, smoothing: Float): Vector<Float> {
	val myVelocity = +source.velocity
	val enemyVelocity = +target.velocity

	val smoothingFactor = 40F / smoothing
	enemyPos.x += (enemyVelocity[0] / 100F) * smoothingFactor
	enemyPos.y += (enemyVelocity[1] / 100F) * smoothingFactor
	enemyPos.z += (enemyVelocity[2] / 100F) * smoothingFactor
	enemyPos.x -= (myVelocity[0] / 100F) * smoothingFactor
	enemyPos.y -= (myVelocity[1] / 100F) * smoothingFactor
	enemyPos.z -= (myVelocity[2] / 100F) * smoothingFactor

	return enemyPos
}

fun angleSmooth(dest: Vector<Float>, orig: Vector<Float>, smoothing: Float) {
	dest.x -= orig[0]
	dest.y -= orig[1]
	dest.z = 0F
	normalizeAngle(dest)

	dest.x = orig[0] + dest.x / 100F * smoothing
	dest.y = orig[1] + dest.y / 100F * smoothing
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

	angles.x = (atan(dZ.toDouble() / hyp) * (180 / Math.PI) - myPunch[0] * pitchReduction).toFloat()
	angles.y = (atan(dY.toDouble() / dX) * (180 / Math.PI) - myPunch[1] * yawReduction).toFloat()
	angles.z = 0F
	if (dX >= 0) angles.y += 180

	return angles
}