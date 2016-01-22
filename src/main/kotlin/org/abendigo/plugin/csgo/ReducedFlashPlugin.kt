package org.abendigo.plugin.csgo

import org.abendigo.csgo.Me
import org.abendigo.csgo.csgo
import org.abendigo.csgo.offsets.m_flFlashMaxAlpha
import org.abendigo.every
import org.abendigo.plugin.Plugin

object ReducedFlashPlugin : Plugin("Reduced Flash", author = "Jire", description = "Reduces the effect of flashbangs") {

	override fun enable() = every(256) {
		if (csgo.get<Float>(Me().address + m_flFlashMaxAlpha) > 0F)
			csgo.set(Me().address + m_flFlashMaxAlpha, 95F)
	}

}