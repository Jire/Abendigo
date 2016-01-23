package org.abendigo.plugin.csgo

import org.abendigo.csgo.Me
import org.abendigo.csgo.csgo
import org.abendigo.csgo.offsets.m_flFlashMaxAlpha

object ReducedFlashPlugin : InGamePlugin("Reduced Flash", author = "Jire",
		description = "Reduces the effect of flashbangs", duration = 256) {

	override fun cycle() {
		if (csgo.get<Float>(Me().address + m_flFlashMaxAlpha) > 0F)
			csgo.set(Me().address + m_flFlashMaxAlpha, 95F)
	}

}