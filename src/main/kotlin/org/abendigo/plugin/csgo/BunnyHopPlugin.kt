package org.abendigo.plugin.csgo

import org.abendigo.csgo.Client.clientDLL
import org.abendigo.csgo.Me
import org.abendigo.csgo.offsets.m_dwForceJump
import org.abendigo.plugin.sleep
import org.abendigo.util.random
import org.jire.kotmem.Keys
import java.awt.event.KeyEvent

object BunnyHopPlugin : InGamePlugin("Bunny Hop", duration = 8) {

	override val author = "Jire"
	override val description = "Jumps the player around"

	private const val MIN_DELAY = 2
	private const val MAX_DELAY = 6

	private const val FAIL_CHANCE = 5

	private var holdingSpace = false
	private var firstJump = true

	override fun cycle() {
		holdingSpace = Keys[KeyEvent.VK_SPACE]
		if (!holdingSpace) {
			firstJump = true
			return
		}
		if (+Me.flags % 2 == 1) {
			var delay = random(MIN_DELAY, MAX_DELAY)
			if (!firstJump && random(FAIL_CHANCE) == 0) delay = duration
			sleep(delay)
			clientDLL[m_dwForceJump] = 5.toByte()
			sleep(random(duration, duration * 2))
			clientDLL[m_dwForceJump] = 4.toByte()
			sleep(32 - duration)
			firstJump = false
		}
	}

}