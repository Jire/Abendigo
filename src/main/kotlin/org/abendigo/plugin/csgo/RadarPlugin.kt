package org.abendigo.plugin.csgo

import org.abendigo.csgo.Client

object RadarPlugin : InGamePlugin("Radar", duration = 8) {

	override val author = "Jire"
	override val description = "Shows enemies on the radar"

	override fun cycle() {
		for ((i, e) in Client.enemies) if (!e.spotted(duration)) e.spotted[true]
	}

}