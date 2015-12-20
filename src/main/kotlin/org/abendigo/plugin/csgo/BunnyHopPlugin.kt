package org.abendigo.plugin.csgo

import org.abendigo.csgo.*
import org.abendigo.every
import org.abendigo.plugin.Plugin
import org.abendigo.sleep
import org.jire.kotmem.keys
import java.awt.event.KeyEvent

class BunnyHopPlugin : Plugin("Bunny Hop", description = "Jumps the player around", author = "Jire") {

	override fun enable() = every(8) {
		if (keys[KeyEvent.VK_SPACE] && +me.flags % 2 == 1) {
			client.set(m_dwForceJump, 5)
			sleep(32)
			client.set(m_dwForceJump, 4)
			sleep(32 - 8)
		}
	}

}