@file:JvmName("Abendigo")

package org.abendigo

import org.abendigo.csgo.entities
import org.abendigo.plugin.csgo.BunnyHopPlugin
import org.abendigo.plugin.csgo.ESPPlugin
import java.lang.management.ManagementFactory
import java.util.concurrent.TimeUnit.SECONDS

fun main(args: Array<String>) {
	println("Process ${ManagementFactory.getRuntimeMXBean().name}")

	every(4, SECONDS) { +entities }

	// TODO make a proper plugin system
	BunnyHopPlugin().enable()
	//RadarPlugin().enable()
	ESPPlugin().enable()
	// AimAssistPlugin().enable()
}