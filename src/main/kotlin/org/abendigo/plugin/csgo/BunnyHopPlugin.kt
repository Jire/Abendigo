package org.abendigo.plugin.csgo

import co.paralleluniverse.fibers.Suspendable
import org.abendigo.*
import org.abendigo.csgo.*
import org.abendigo.plugin.Plugin
import org.jire.kotmem.keys
import java.awt.event.KeyEvent

class BunnyHopPlugin : Plugin("Bunny Hop", description = "Jumps the player around", author = "Jire") {

	override fun enable() {
		every(8) @Suspendable {
			if (keys[KeyEvent.VK_SPACE] && +me.flags % 2 == 1) {
				client.set(m_dwForceJump, 5)
				sleep(32)
				client.set(m_dwForceJump, 4)
				sleep(32 - 8)
			}
		}
	}

}