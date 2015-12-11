package org.abendigo.plugin.csgo

import co.paralleluniverse.fibers.Suspendable
import org.abendigo.csgo.*
import org.abendigo.every
import org.abendigo.plugin.Plugin

class RadarPlugin : Plugin("Radar", description = "Shows enemies on the radar", author = "Jire") {

	override fun enable() {
		every(8) @Suspendable {
			val myTeam: Int = csgo.get(me().address + m_iTeamNum)
			for (i in 0..+objectCount - 1) {
				val objectAddress: Int = client.get(m_dwEntityList + (i * ENTITY_SIZE))
				if (objectAddress > 0) {
					val team: Int = csgo.get(objectAddress + m_iTeamNum)
					if ((team == 2 || team == 3) && team != myTeam) csgo.set(objectAddress + m_bSpotted, true)
				}
			}
		}
	}

}