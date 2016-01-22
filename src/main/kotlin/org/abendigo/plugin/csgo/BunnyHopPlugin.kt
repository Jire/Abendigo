package org.abendigo.plugin.csgo

import org.abendigo.csgo.Me
import org.abendigo.csgo.clientDLL
import org.abendigo.csgo.offsets.m_dwForceJump
import org.abendigo.every
import org.abendigo.plugin.Plugin
import org.abendigo.sleep
import org.jire.kotmem.Keys
import java.awt.event.KeyEvent

object BunnyHopPlugin : Plugin("Bunny Hop", author = "Jire", description = "Jumps the player around") {

	override fun enable() = every(8) {
		if (Keys[KeyEvent.VK_SPACE] && +Me.flags % 2 == 1) {
			clientDLL.set(m_dwForceJump, 5)
			sleep(32)
			clientDLL.set(m_dwForceJump, 4)
			sleep(32 - 8)
		}
	}

}