package org.abendigo.plugin.csgo

import org.abendigo.csgo.Client

object RadarPlugin : InGamePlugin("Radar", author = "Jire", description = "Shows enemies on the radar", duration = 8) {

	override fun cycle() {
		for ((i, e) in Client.enemies) if (!+e.spotted) e.spotted[true]
	}

}