package org.abendigo.plugin.csgo

import org.abendigo.DEBUG
import org.abendigo.csgo.Client.clientDLL
import org.abendigo.csgo.Client.enemies
import org.abendigo.csgo.Me
import org.abendigo.csgo.offsets.m_dwForceAttack
import org.abendigo.plugin.sleep
import org.abendigo.util.random

object TriggerBotPlugin : InGamePlugin(name = "Trigger Bot", duration = 10) {

	override val author = "Jire"
	override val description = "Fires when your crosshair is on an enemy"

	private const val LEGIT = true

	private const val MIN_SCOPE_DURATION = 85
	private const val MAX_SCOPE_DURATION = 160

	private const val BOLT_ACTION_ONLY = true

	private var scopeDuration = 0

	override fun cycle() {
		val scoped = +Me.inScope
		if (!scoped) {
			scopeDuration = 0
			return
		}

		scopeDuration += duration
		if (scopeDuration < random(MIN_SCOPE_DURATION, MAX_SCOPE_DURATION)) return

		try {
			val weapon = (+Me().weapon).type!!
			if (!weapon.sniper || (BOLT_ACTION_ONLY && !weapon.boltAction)) return
		} catch (t: Throwable) {
			if (DEBUG) t.printStackTrace()
		}

		for ((i, e) in enemies) if (e.address == +Me.targetAddress) {
			if (LEGIT) {
				val spotted = +e.spotted
				sleep(random(duration, if (spotted) duration * 3 else duration * 5))
			}
			clientDLL[m_dwForceAttack] = 5.toByte()
			sleep(random(duration, duration * 2))
			clientDLL[m_dwForceAttack] = 4.toByte()
		}
	}

}
