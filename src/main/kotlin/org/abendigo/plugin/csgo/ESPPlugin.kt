package org.abendigo.plugin.csgo

import org.abendigo.csgo.*
import org.abendigo.csgo.Client.glowObject
import org.abendigo.csgo.Client.glowObjectCount
import org.abendigo.csgo.Client.players

object ESPPlugin : InGamePlugin("ESP", duration = 64) {

	override val author = "Jire"
	override val description = "Outlines players"

	override fun cycle() {
		for (glIdx in 0..+glowObjectCount) {
			val glOffset: Int = glowObject(64) + (glIdx * GLOW_OBJECT_SIZE)
			val glOwner: Int = csgo[glOffset]
			if (glOwner <= 0) continue
			for ((i, p) in players) {
				if (glOwner != p.address || p.dead(8)) continue

				var red = 255F
				var green = 0F
				var blue = 0F
				var alpha = 0.8F

				val myTeam = Me().team(64)
				val pTeam = p.team(64)
				if (myTeam == pTeam) {
					red = 0F
					blue = 255F
				} else if (p.address == +Me.targetAddress) green = 215F
				else if (+p.dormant) {
					blue = 255F
					green = 255F
					alpha = 0.45F
				}

				csgo[glOffset + 0x4] = red
				csgo[glOffset + 0x8] = green
				csgo[glOffset + 0xC] = blue
				csgo[glOffset + 0x10] = alpha
				csgo[glOffset + 0x24] = true
				csgo[glOffset + 0x25] = false
			}
		}
	}

}