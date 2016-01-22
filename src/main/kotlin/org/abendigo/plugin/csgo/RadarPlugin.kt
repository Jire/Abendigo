package org.abendigo.plugin.csgo

import org.abendigo.csgo.Client.enemies
import org.abendigo.plugin.Plugin
import org.abendigo.plugin.every

object RadarPlugin : Plugin("Radar", author = "Jire", description = "Shows enemies on the radar") {

	override fun enable() = every(8) {
		for ((i, e) in enemies) if (!+e.spotted) e.spotted[true]
	}

}