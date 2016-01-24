package org.abendigo.plugin.csgo

import org.abendigo.csgo.*
import org.abendigo.csgo.Engine.clientState

object RCSPlugin : InGamePlugin(name = "Recoil Control System", author = "Jire",
		description = "Controls your recoil", duration = 32) {

	const val r3 = 2F

	@Volatile private var pPunchX = 0F
	@Volatile private var pPunchY = 0F

	@Volatile private var prevFired = 0

	override fun cycle() {
		val shotsFired = +Me().shotsFired
		if (shotsFired < 1 || shotsFired < prevFired) {
			prevFired = 0
			return
		}

		// TODO check weapon (for example, weapon ID and remaining ammo)
		if (shotsFired > 1 && shotsFired >= prevFired) {
			val punch = Me.punch()
			val m_punch = Vector2(punch.a * r3, punch.b * r3)
			val currentAngles = (+clientState).angle()
			val modifier = Vector2(m_punch.a - pPunchX, m_punch.b - pPunchY)
			val newAngle = clampAngle(Vector2(currentAngles.a - modifier.a, currentAngles.b - modifier.b))
			(+clientState).angle(newAngle)
		}
	}

}