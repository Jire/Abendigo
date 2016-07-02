@file:JvmName("Abendigo")

package org.abendigo

import org.abendigo.controller.Server
import org.abendigo.csgo.Client.clientDLL
import org.abendigo.csgo.Client.entities
import org.abendigo.csgo.Me
import org.abendigo.csgo.csgo
import org.abendigo.csgo.engineDLL
import org.abendigo.csgo.offsets.netVars
import org.abendigo.plugin.Plugins.enable
import org.abendigo.plugin.csgo.*
import org.abendigo.plugin.every
import java.util.concurrent.TimeUnit.SECONDS

fun main(args: Array<String>) {
	Server.bind()

	while (!Thread.interrupted()) try {
		csgo
		engineDLL
		clientDLL
		netVars
		break
	} catch (t: Throwable) {
		Thread.sleep(1500)
	}

	every(2, SECONDS) {
		+Me
		+entities
	}

	enable(ESPPlugin)
	enable(BunnyHopPlugin)
	enable(AimAssistPlugin)
	enable(SprayAssistPlugin)
	enable(TriggerBotPlugin)

	// enable(RadarPlugin) // bSpotted is being checked by client, DO NOT USE
	// enable(ReducedFlashPlugin) // flash value is being checked, DO NOT USE
	// enable(RCSPlugin) // the way we're computing new angle is being detected, DO NOT USE
}