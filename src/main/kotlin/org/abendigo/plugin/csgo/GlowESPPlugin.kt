package org.abendigo.plugin.csgo

import org.abendigo.csgo.*
import org.abendigo.csgo.Client.glowObject
import org.abendigo.csgo.Client.glowObjectCount

object GlowESPPlugin : InGamePlugin("ESP", duration = 64) {

	override val author = "Jire"
	override val description = "Outlines players with a glow"

	private const val SHOW_TEAM = true
	private const val SHOW_DORMANT = false

	private const val SHOW_WEAPONS = true
	private const val SHOW_BOMB = true

	private const val MODEL_COLORS = true

	private const val ALPHA = 0.8F
	private const val REDUCE_ALPHA_UNSPOTTED = 0.4F /* set to 1.0F to not reduce */

	override fun cycle() {
		glow@ for (glIdx in 0..+glowObjectCount) {
			val glowAddress: Int = +glowObject + (glIdx * GLOW_OBJECT_SIZE)
			if (glowAddress <= 0) continue

			val entityAddress: Int = csgo[glowAddress]
			if (entityAddress <= 0) continue

			for ((i, p) in Client.players) {
				if (entityAddress != p.address || +p.dead) continue

				val dormant = +p.dormant
				if (!SHOW_DORMANT && dormant) continue

				var red = 255F
				var green = 0F
				var blue = 0F
				var alpha = ALPHA

				if (!+p.spotted) alpha *= REDUCE_ALPHA_UNSPOTTED

				val myTeam = +Me().team
				val pTeam = +p.team
				if (myTeam == pTeam) {
					if (!SHOW_TEAM) continue
					red = 0F
					blue = 255F
				} else if (p.address == +Me.targetAddress) green = 215F
				else if (SHOW_DORMANT && dormant) {
					blue = 255F
					green = 255F
					alpha = 0.45F
				}

				glow(glowAddress, red, green, blue, alpha)

				if (MODEL_COLORS) modelColors(entityAddress, red.toByte(), green.toByte(), blue.toByte())

				continue@glow
			}

			val cls = EntityType.byEntityAddress(entityAddress) ?: continue
			if (SHOW_WEAPONS && (cls.weapon || cls.grenade)) glow(glowAddress, 0F, 255F, 0F, 0.5F)
			else if (SHOW_BOMB && (cls == EntityType.CC4 || cls == EntityType.CPlantedC4
					|| cls == EntityType.CTEPlantBomb)) glow(glowAddress, 255F, 255F, 255F)
		}
	}

	private fun glow(glowAddress: Int, red: Float, green: Float, blue: Float, alpha: Float = 1F,
	                 occluded: Boolean = true, unoccluded: Boolean = false, fullBloom: Boolean = false) {
		csgo[glowAddress + 0x4] = red
		csgo[glowAddress + 0x8] = green
		csgo[glowAddress + 0xC] = blue
		csgo[glowAddress + 0x10] = alpha
		csgo[glowAddress + 0x24] = occluded
		csgo[glowAddress + 0x25] = unoccluded
		csgo[glowAddress + 0x26] = fullBloom
	}

	private fun modelColors(entityAddress: Int, red: Byte, green: Byte, blue: Byte, alpha: Byte = 255.toByte()) {
		csgo[entityAddress + 0x70] = red
		csgo[entityAddress + 0x71] = green
		csgo[entityAddress + 0x72] = blue
		csgo[entityAddress + 0x73] = alpha
	}

}