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

const val TRUE_MOUSE_MOVEMENT = false // must be true to bypass FaceIT (must configure below settings)
const val IN_GAME_SENS = 2.5F // set this to your in-game sensitivity
const val IN_GAME_PITCH = 0.022F
const val IN_GAME_YAW = 0.022F

fun main(args: Array<String>) {
	if (DEBUG) println(ManagementFactory.getRuntimeMXBean().name)

	// enable this if you want to use controller
	Server.bind().syncUninterruptibly()

	while (!Thread.interrupted()) try {
		csgo
		csgo.loadModules()
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

	enable(FOVAimPlugin) // I recommend not using any other aim plugins if you use FOV

	// enable(AimAssistPlugin)
	// enable(SprayAssistPlugin)
	// enable(RCSPlugin)

	enable(TriggerBotPlugin)
	// enable(BoneTriggerPlugin)

	enable(BunnyHopPlugin)
	enable(ReducedFlashPlugin)
	// enable(GlowModelsPlugin) //Turn off when using GlowESP
	// enable(SkinChangerPlugin)
}
