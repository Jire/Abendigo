package org.abendigo.plugin.csgo

import org.abendigo.csgo.Me
import org.abendigo.csgo.csgo
import org.abendigo.csgo.offsets.m_flFlashMaxAlpha
import org.jire.arrowhead.get

object NoFlashPlugin : InGamePlugin("No Flash", duration = 256) {

	override val author = "Jire"
	override val description = "Simple no flash"

	private const val REDUCTION_PERCENTAGE = 0

	override fun cycle() {
		if (csgo.get<Float>(Me().address + m_flFlashMaxAlpha) > 0F)
			csgo[Me().address + m_flFlashMaxAlpha] = 255F * (REDUCTION_PERCENTAGE / 100F)
	}

}