package org.abendigo.plugin.csgo

import org.abendigo.plugin.CyclePlugin
import org.abendigo.plugin.every
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.MILLISECONDS

abstract class InGamePlugin(name: String, duration: Int,
                            durationUnit: TimeUnit = MILLISECONDS) : CyclePlugin(name, duration, durationUnit) {

	override fun enable() = every(duration, durationUnit) {
		// TODO
		/*val inGame = engineDLL.get<Int>(m_dwInGame)//+Engine.inGame
		if (inGame == 6)*/
		cycle()
		//else println(inGame)
	}

}