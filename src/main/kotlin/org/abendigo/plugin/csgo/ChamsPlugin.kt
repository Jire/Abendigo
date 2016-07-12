package org.abendigo.plugin.csgo

import org.abendigo.csgo.*
import org.abendigo.csgo.Client.glowObject
import org.abendigo.csgo.Client.glowObjectCount
import org.abendigo.csgo.Client.players

object ChamsPlugin : InGamePlugin("Chams", duration = 64) {

	override val author = "Jire"
	override val description = "Changes player model colors"

	private const val SHOW_TEAM = true
	private const val SHOW_DORMANT = true

	override fun cycle() {
		for ((i, e) in Client.enemies) {
			csgo[e.address + 0x70] = 255.toByte()
			csgo[e.address + 0x71] = 0.toByte()
			csgo[e.address + 0x72] = 0.toByte()
			csgo[e.address + 0x73] = 255.toByte()
		}
		for ((i, e) in Client.team) {
			csgo[e.address + 0x70] = 0.toByte()
			csgo[e.address + 0x71] = 0.toByte()
			csgo[e.address + 0x72] = 255.toByte()
			csgo[e.address + 0x73] = 255.toByte()
		}
	}

}