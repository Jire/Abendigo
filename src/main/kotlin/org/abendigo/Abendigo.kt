@file:JvmName("Abendigo")

package org.abendigo

import org.abendigo.csgo.Client.clientDLL
import org.abendigo.csgo.Client.entities
import org.abendigo.csgo.csgo
import org.abendigo.csgo.engineDLL
import org.abendigo.csgo.offsets.netVars
import org.abendigo.plugin.csgo.*
import org.abendigo.plugin.enable
import org.abendigo.plugin.every
import java.lang.management.ManagementFactory
import java.util.concurrent.TimeUnit.SECONDS

fun main(args: Array<String>) {
	println("Process ${ManagementFactory.getRuntimeMXBean().name}")

	while (!Thread.interrupted()) try {
		csgo
		engineDLL
		clientDLL
		netVars
		break
	} catch (t: Throwable) {
		Thread.sleep(1500)
	}

	every(2, SECONDS) { +entities }

	// TODO make a proper plugin system
	enable(BunnyHopPlugin) // is very fast and can look not legit if you don't manually fail jumps
	// enable(RadarPlugin) // bSpotted is being checked by client, DO NOT USE
	enable(ESPPlugin)
	enable(AimAssistPlugin)
	// enable(ReducedFlashPlugin) // flash value is being checked, DO NOT USE
	// enable(TriggerBotPlugin) // trigger doesn't look legit, be careful using
	enable(PistolAssistPlugin)
}