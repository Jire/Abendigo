package org.abendigo.plugin.csgo

import org.abendigo.plugin.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.MILLISECONDS

abstract class InGamePlugin(info: PluginInfo, duration: Int,
                            durationUnit: TimeUnit = MILLISECONDS) : CyclePlugin(info, duration, durationUnit) {

	override fun enable() = every(duration, durationUnit) {
		// TODO
		/*val inGame = engineDLL.get<Int>(m_dwInGame)//+Engine.inGame
		if (inGame == 6)*/
		cycle()
		//else println(inGame)
	}

}