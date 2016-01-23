package org.abendigo.plugin.csgo

import org.abendigo.plugin.CyclePlugin
import org.abendigo.plugin.every
import java.util.concurrent.TimeUnit

abstract class InGamePlugin(name: String, version: String = "1.0", author: String = "",
                            description: String = "", duration: Int, durationUnit: TimeUnit = TimeUnit.MILLISECONDS)
: CyclePlugin(name, version, author, description, duration, durationUnit) {

	override fun enable() = every(duration, durationUnit) {
		// TODO
		/*val inGame = engineDLL.get<Int>(m_dwInGame)//+Engine.inGame
		if (inGame == 6)*/
			cycle()
		//else println(inGame)
	}

}