package org.abendigo.plugin.csgo

import org.abendigo.csgo.Client.enemies
import org.abendigo.csgo.Me
import org.abendigo.csgo.clientDLL
import org.abendigo.csgo.offsets.m_dwForceAttack
import org.abendigo.plugin.sleep

object TriggerBotPlugin : InGamePlugin(name = "Trigger Bot", author = "Jire",
		description = "Fires when your crosshair is on an enemy", duration = 32) {

	override fun cycle() {
		for ((i, e) in enemies) if (e.address == Me.targetAddress(32)) {
			clientDLL.set(m_dwForceAttack, 5.toByte())
			sleep(32)
			clientDLL.set(m_dwForceAttack, 4.toByte())
		}
	}

}