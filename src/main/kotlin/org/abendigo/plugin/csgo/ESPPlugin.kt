package org.abendigo.plugin.csgo

import org.abendigo.csgo.*
import org.abendigo.every
import org.abendigo.plugin.Plugin

class ESPPlugin : Plugin("ESP", author = "Jire", description = "Outlines players") {

	override fun enable() = every(64) {
		val glowPointer: Int = client.get(m_dwGlowObject)
		val glowCount: Int = client.get(m_dwGlowObject + 4)
		for (gi in 0..glowCount) {
			val go = glowPointer + (gi * GLOW_OBJECT_SIZE)
			val ownerAddress: Int = csgo.get(go)
			if (ownerAddress > 0) {
				for ((i, p) in players) {
					if (ownerAddress == p.address) {
						if (p.lifeState(64) < 0) continue

						var red = 255F
						var green = 0F
						var blue = 0F
						var alpha = 0.8F

						if (me().team(64) == p.team(64)) {
							red = 0F
							blue = 255F
						} else if (+p.dormant) {
							blue = 255F
							green = 255F
							alpha = 0.45F
						}

						csgo.set(go + 0x4, red)
						csgo.set(go + 0x8, green)
						csgo.set(go + 0xC, blue)
						csgo.set(go + 0x10, alpha)
						csgo.set(go + 0x24, true)
						csgo.set(go + 0x25, false)
					}
				}
			}
		}
	}

}