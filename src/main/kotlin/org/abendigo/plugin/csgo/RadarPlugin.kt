package org.abendigo.plugin.csgo

import org.abendigo.csgo.*
import org.abendigo.every
import org.abendigo.plugin.Plugin

class RadarPlugin : Plugin("Radar", description = "Shows enemies on the radar", author = "Jire") {

	override fun enable() {
		every(8) { for ((i, e) in enemies) csgo.set(e.address + m_bSpotted, true) }
	}

}