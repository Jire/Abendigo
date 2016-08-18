@file:JvmName("Abendigo")

package org.abendigo

import org.abendigo.controller.Server
import org.abendigo.csgo.*
import org.abendigo.csgo.Client.clientDLL
import org.abendigo.csgo.Client.entities
import org.abendigo.csgo.offsets.netVars
import org.abendigo.plugin.Plugins.enable
import org.abendigo.plugin.csgo.*
import org.abendigo.plugin.every
import java.lang.management.ManagementFactory
import java.util.concurrent.TimeUnit.SECONDS

const val DEBUG = false

fun main(args: Array<String>) {
	if (DEBUG) println(ManagementFactory.getRuntimeMXBean().name)

	Server.bind().syncUninterruptibly()

	while (!Thread.interrupted()) try {
		csgo
		csgoModule
		engineDLL
		clientDLL
		netVars
		break
	} catch (t: Throwable) {
		if (DEBUG) t.printStackTrace()
		Thread.sleep(1500)
	}

	// FakeLagPlugin.disable() // only need this if you're using fake lag

	every(2, SECONDS) {
		+Me
		+entities
	}


	enable(GlowESPPlugin)
	enable(BunnyHopPlugin)
	enable(TriggerBotPlugin)
	// enable(BoneTriggerPlugin)
	enable(ReducedFlashPlugin)
	enable(SkinChangerPlugin)
	// enable(FakeLagPlugin)
	//enable(FOVAimPlugin) // I recommend not using any other aim plugins if you use FOV
	// enable(AimAssistPlugin) // do not use with FOV aim
	// enable(SprayAssistPlugin) // do not use with RCS
	// enable(RCSPlugin) // do not use with spray assist


	// --- !!! DANGER ZONE !!! --- //
	// enable(RadarPlugin) // bSpotted is being checked by client, DO NOT USE
}
