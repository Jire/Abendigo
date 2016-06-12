package org.abendigo.plugin.csgo

import org.abendigo.csgo.Client.clientDLL
import org.abendigo.csgo.Client.enemies
import org.abendigo.csgo.Me
import org.abendigo.csgo.offsets.m_dwForceAttack
import org.abendigo.plugin.sleep
import org.abendigo.util.random

object TriggerBotPlugin : InGamePlugin(name = "Trigger Bot", duration = 32) {

	override val author = "Jire"
	override val description = "Fires when your crosshair is on an enemy"

	private val MIN_DELAY = duration
	private val MAX_DELAY = duration * 4

	override fun cycle() {
		if (!+Me.inScope) return

		try {
			val weapon = +Me.weapon
			val weaponID = +weapon.id
			if (weaponID != 9 || weaponID != 40) return
		} catch (t: Throwable) {
			// sometimes it fails to read weapon
		}

		for ((i, e) in enemies) if (e.address == +Me.targetAddress) {
			sleep(random(MIN_DELAY, MAX_DELAY))
			clientDLL[m_dwForceAttack] = 5.toByte()
			sleep(random(MIN_DELAY / 2, MAX_DELAY / 2))
			clientDLL[m_dwForceAttack] = 4.toByte()
		}
	}

}