package org.abendigo.plugin.csgo

import org.abendigo.csgo.Client.clientDLL
import org.abendigo.csgo.Client.enemies
import org.abendigo.csgo.Me
import org.abendigo.csgo.offsets.m_dwForceAttack
import org.abendigo.plugin.sleep

object TriggerBotPlugin : InGamePlugin(name = "Trigger Bot", duration = 32) {

	override val author = "Jire"
	override val description = "Fires when your crosshair is on an enemy"

	override fun cycle() {
		for ((i, e) in enemies) if (e.address == Me.targetAddress(duration)) {
			clientDLL[m_dwForceAttack] = 5.toByte()
			sleep(32) // 32 = snipers, 128 = overshoot for automatic weapons
			clientDLL[m_dwForceAttack] = 4.toByte()
		}
	}

}