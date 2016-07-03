package org.abendigo.plugin.csgo

import org.abendigo.DEBUG
import org.abendigo.csgo.Engine.clientState
import org.abendigo.csgo.GameState
import org.abendigo.plugin.CyclePlugin
import org.abendigo.plugin.every
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.MILLISECONDS

abstract class InGamePlugin(name: String, duration: Int,
                            durationUnit: TimeUnit = MILLISECONDS) : CyclePlugin(name, duration, durationUnit) {

	override fun enable() = every(duration, durationUnit) {
		val gameState = clientState(1024).gameState()
		if (enabled && GameState.PLAYING == gameState) try {
			cycle()
		} catch (t: Throwable) {
			if (DEBUG) t.printStackTrace()
		}
	}

}