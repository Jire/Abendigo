package org.abendigo.plugin.csgo

import org.abendigo.DEBUG
import org.abendigo.csgo.engineDLL
import org.abendigo.csgo.offsets.m_bSendPacket
import org.abendigo.plugin.sleep
import org.abendigo.util.random
import org.jire.arrowhead.get

object FakeLagPlugin : InGamePlugin("Fake Lag", duration = 1) {

	private const val MIN_DURATION = 80
	private const val MAX_DURATION = 310

	override fun cycle() {
		try {
			val sendPacket: Boolean = engineDLL[m_bSendPacket]
			engineDLL[m_bSendPacket] = !sendPacket
			sleep(random(MIN_DURATION, MAX_DURATION))
		} catch (t: Throwable) {
			if (DEBUG) t.printStackTrace()
		}
	}

	override fun disable() {
		engineDLL[m_bSendPacket] = true
	}

}