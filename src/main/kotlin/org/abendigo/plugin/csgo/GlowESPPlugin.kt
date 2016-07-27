package org.abendigo.plugin.csgo

import org.abendigo.csgo.*
import org.abendigo.csgo.Client.glowObject
import org.abendigo.csgo.Client.glowObjectCount
import org.abendigo.csgo.offsets.m_bDormant
import org.jire.arrowhead.get

object GlowESPPlugin : InGamePlugin("Glow ESP", duration = 64) {

	override val author = "Jire"
	override val description = "Outlines players with a glow"

	private const val PLAYER_ALPHA = 0.8F
	private const val REDUCE_ALPHA_UNSPOTTED = 0.7F /* set to 1.0F to not reduce */

	private const val SHOW_TEAM = true
	private const val SHOW_DORMANT = false

	private const val SHOW_BOMB = true
	private const val SHOW_WEAPONS = true
	private const val SHOW_GRENADES = true
	private const val SHOW_CHICKENS = false

	private const val CHANGE_MODEL_COLORS = true

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

				var red = 255
				var green = 0
				var blue = 0
				var alpha = PLAYER_ALPHA

				if (!+p.spotted) alpha *= REDUCE_ALPHA_UNSPOTTED

				val myTeam = +Me().team
				val pTeam = +p.team
				if (myTeam == pTeam) {
					if (!SHOW_TEAM) continue
					red = 0
					blue = 255
				} else if (SHOW_DORMANT && dormant) {
					blue = 255
					green = 255
					alpha = 0.75F
				}

				if (p.address == +Me.targetAddress) green = 215
				else if (p.hasWeapon(Weapons.C4)) {
					blue = 255
					green = 0
					red = 255
					alpha = 0.8F
				}


				glow(glowAddress, red, green, blue, alpha)

				if (CHANGE_MODEL_COLORS) modelColors(entityAddress, red.toByte(), green.toByte(), blue.toByte())

				continue@glow
			}

			val type = EntityType.byEntityAddress(entityAddress) ?: continue
			if (SHOW_WEAPONS && type.weapon) glow(glowAddress, 0, 255, 0, 0.75F)
			else if (SHOW_GRENADES && (type.grenade || type == EntityType.CSpatialEntity
					|| type == EntityType.CMovieDisplay || type == EntityType.CSpotlightEnd))
				glow(glowAddress, 0, 255, 0)
			else if (SHOW_CHICKENS && type == EntityType.CChicken) {
				val dormant: Boolean = csgo[entityAddress + m_bDormant]
				if (!dormant) glow(glowAddress, 255, 0, 0)
			} else if (SHOW_BOMB && (type == EntityType.CC4 || type == EntityType.CPlantedC4
					|| type == EntityType.CTEPlantBomb || type == EntityType.CPlasma)) glow(glowAddress, 255, 0, 255)
		}
	}

	private fun glow(glowAddress: Int, red: Int, green: Int, blue: Int, alpha: Float = 1F,
	                 occluded: Boolean = true, unoccluded: Boolean = false, fullBloom: Boolean = false) {
		csgo[glowAddress + 0x4] = red / 255F
		csgo[glowAddress + 0x8] = green / 255F
		csgo[glowAddress + 0xC] = blue / 255F
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