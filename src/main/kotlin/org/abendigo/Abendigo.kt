@file:JvmName("Abendigo")

package org.abendigo

import org.abendigo.csgo.Client.clientDLL
import org.abendigo.csgo.Client.entities
import org.abendigo.csgo.Me
import org.abendigo.csgo.csgo
import org.abendigo.csgo.engineDLL
import org.abendigo.csgo.offsets.netVars
import org.abendigo.plugin.csgo.*
import org.abendigo.plugin.enable
import org.abendigo.plugin.every
import java.util.concurrent.TimeUnit.SECONDS

fun main(args: Array<String>) {
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

	// TODO make a proper plugin system
	enable(BunnyHopPlugin)
	// enable(RadarPlugin) // bSpotted is being checked by client, DO NOT USE
	enable(ESPPlugin)
	enable(SprayAssistPlugin)
	// enable(ReducedFlashPlugin) // flash value is being checked, DO NOT USE
	enable(TriggerBotPlugin)
	enable(AimAssistPlugin)
	// enable(RCSPlugin) // the way we're computing new angle is being detected, DO NOT USE
}