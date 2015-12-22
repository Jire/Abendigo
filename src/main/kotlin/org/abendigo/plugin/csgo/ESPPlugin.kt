package org.abendigo.plugin.csgo

import org.abendigo.csgo.*
import org.abendigo.every
import org.abendigo.plugin.Plugin

class ESPPlugin : Plugin("ESP", author = "Jire", description = "Outlines players") {

	override fun enable() = every(64) {
		for (glIdx in 0..+glowObjectCount) {
			val glOffset = glowObject(64) + (glIdx * GLOW_OBJECT_SIZE)
			val glOwner: Int = csgo.get(glOffset)
			if (glOwner < 0) continue
			for ((i, p) in players) {
				if (glOwner != p.address || p.lifeState(64) < 0) continue
				var red = 255F
				var green = 0F
				var blue = 0F
				var alpha = 0.8F

				if (me().team(64) == p.team(64)) {
					red = 0F
					blue = 255F
				} else if (p.id == me.targetID(64)) green = 215F
				else if (p.dormant(8)) {
					blue = 255F
					green = 255F
					alpha = 0.45F
				}

				csgo.set(glOffset + 0x4, red)
				csgo.set(glOffset + 0x8, green)
				csgo.set(glOffset + 0xC, blue)
				csgo.set(glOffset + 0x10, alpha)
				csgo.set(glOffset + 0x24, true)
				csgo.set(glOffset + 0x25, false)
			}
		}
	}

}