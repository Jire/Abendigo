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
import java.lang.management.ManagementFactory
import java.util.concurrent.TimeUnit.SECONDS

const val DEBUG = false

fun main(args: Array<String>) {
	if (DEBUG) println(ManagementFactory.getRuntimeMXBean().name)

	Server.bind().syncUninterruptibly()

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

	enable(GlowESPPlugin)
	enable(BunnyHopPlugin)
	enable(TriggerBotPlugin)
	enable(ReducedFlashPlugin) // still unsure if can cause untrusted, disable if you want to be safer

	enable(FOVAimPlugin) // do not use with aim assist
	//enable(AimAssistPlugin) // do not use with FOV aim

	//enable(RCSPlugin) // do not use with spray assist
	//enable(SprayAssistPlugin) // do not use with RCS


	// --- !!! DANGER ZONE !!! --- //
	// enable(RadarPlugin) // bSpotted is being checked by client, DO NOT USE
}