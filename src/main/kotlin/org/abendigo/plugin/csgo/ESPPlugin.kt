package org.abendigo.plugin.csgo

import org.abendigo.csgo.*
import org.abendigo.every
import org.abendigo.plugin.Plugin

class ESPPlugin : Plugin("ESP", author = "Jire", description = "Outlines players") {

	override fun enable() = every(64) {
		val glowObject = +glowObject
		for (gi in 0..+glowObjectCount) {
			val glowObjectOffset = glowObject + (gi * GLOW_OBJECT_SIZE)
			val glowObjectOwner: Int = csgo.get(glowObjectOffset)
			if (glowObjectOwner > 0) {
				for ((i, p) in players) {
					if (glowObjectOwner == p.address) {
						if (p.lifeState(64) < 0) continue

						var red = 255F
						var green = 0F
						var blue = 0F
						var alpha = 0.8F

						if (me().team(64) == p.team(64)) {
							red = 0F
							blue = 255F
						} else if (p.id == me.targetID(64)) green = 215F
						else if (+p.dormant) {
							blue = 255F
							green = 255F
							alpha = 0.45F
						}

						csgo.set(glowObjectOffset + 0x4, red)
						csgo.set(glowObjectOffset + 0x8, green)
						csgo.set(glowObjectOffset + 0xC, blue)
						csgo.set(glowObjectOffset + 0x10, alpha)
						csgo.set(glowObjectOffset + 0x24, true)
						csgo.set(glowObjectOffset + 0x25, false)
					}
				}
			}
		}
	}

}