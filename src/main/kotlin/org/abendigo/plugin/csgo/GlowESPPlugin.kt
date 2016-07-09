package org.abendigo.plugin.csgo

import org.abendigo.csgo.*
import org.abendigo.csgo.Client.glowObject
import org.abendigo.csgo.Client.glowObjectCount
import org.abendigo.csgo.Client.players

object GlowESPPlugin : InGamePlugin("ESP", duration = 64) {

	override val author = "Jire"
	override val description = "Outlines players with a glow"

	private const val SHOW_TEAM = true
	private const val SHOW_DORMANT = true

	private const val ALPHA = 0.8F
	private const val REDUCE_ALPHA_UNSPOTTED = 0.4F /* set to 1.0F to not reduce */

	override fun cycle() {
		for (glIdx in 0..+glowObjectCount) {
			val glOffset: Int = +glowObject + (glIdx * GLOW_OBJECT_SIZE)
			val glOwner: Int = csgo[glOffset]
			if (glOwner > 0) for ((i, p) in players) {
				if (glOwner != p.address || +p.dead) continue

				var red = 255F
				var green = 0F
				var blue = 0F
				var alpha = ALPHA

				if (!+p.spotted) alpha = ALPHA * REDUCE_ALPHA_UNSPOTTED

				val myTeam = +Me().team
				val pTeam = +p.team
				if (myTeam == pTeam) {
					if (!SHOW_TEAM) continue
					red = 0F
					blue = 255F
				} else if (p.address == +Me.targetAddress) green = 215F
				else if (+p.dormant) {
					if (!SHOW_DORMANT) continue
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