package org.abendigo.plugin.csgo

import org.abendigo.csgo.Engine
import org.abendigo.csgo.GameState
import org.abendigo.plugin.CyclePlugin
import org.abendigo.plugin.every
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.MILLISECONDS

abstract class InGamePlugin(name: String, duration: Int,
                            durationUnit: TimeUnit = MILLISECONDS) : CyclePlugin(name, duration, durationUnit) {

	override fun enable() = every(duration, durationUnit) {
		val gameState = Engine.clientState().gameState()
		if (GameState.PLAYING == gameState)
			cycle()
	}

}