@file:JvmName("Abendigo")

package org.abendigo

import org.abendigo.csgo.Client.entities
import org.abendigo.plugin.csgo.*
import org.abendigo.plugin.enable
import org.abendigo.plugin.every
import java.lang.management.ManagementFactory
import java.util.concurrent.TimeUnit.SECONDS

fun main(args: Array<String>) {
	println("Process ${ManagementFactory.getRuntimeMXBean().name}")

	every(3, SECONDS) { +entities }

	// TODO make a proper plugin system
	enable(BunnyHopPlugin)
	// enable(RadarPlugin) // bSpotted is being checked by client, do not use
	enable(ESPPlugin)
	// enable(AimAssistPlugin)
	// enable(ReducedFlashPlugin)
	enable(TriggerBotPlugin)
}