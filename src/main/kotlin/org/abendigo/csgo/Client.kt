@file:JvmName("Client")

package org.abendigo.csgo

import org.abendigo.DEBUG
import org.abendigo.cached.initializedCache
import org.abendigo.csgo.offsets.m_dwEntityList
import org.abendigo.csgo.offsets.m_dwGlowObject
import org.abendigo.csgo.offsets.m_iTeamNum
import org.jire.arrowhead.get
import java.util.concurrent.ConcurrentHashMap

object Client {

	val clientDLL by lazy(LazyThreadSafetyMode.NONE) { csgo.modules["client.dll"]!! }

	val glowObject = cached<Int>(clientDLL, m_dwGlowObject)

	val glowObjectCount = cached<Int>(clientDLL, m_dwGlowObject + 4)

	val entities = initializedCache({ ConcurrentHashMap<Int, Entity>(256) }) {
		clear()
		players.clear()
		team.clear()
		enemies.clear()

		val myTeam = +Me().team
		for (i in 0..+glowObjectCount - 1) {
			try {
				val address: Int = clientDLL[m_dwEntityList + (i * ENTITY_SIZE)]
				if (+Me().address != address && address > 0) {
					val type = EntityType.byEntityAddress(address)!!
					val entity = Entity(address, i, type)
					put(i, entity)

					if (type != EntityType.CCSPlayer) continue

					val entityTeam: Int = csgo[address + m_iTeamNum]
					if (entityTeam == 2 || entityTeam == 3) {
						val player = Player(entity)
						if (myTeam == entityTeam) team.put(i, player)
						else enemies.put(i, player)
					}
				}
			} catch (t: Throwable) {
				if (DEBUG) t.printStackTrace()
			}
		}
		players.putAll(team)
		players.putAll(enemies)
	}

	val players = ConcurrentHashMap<Int, Player>()
	val team = ConcurrentHashMap<Int, Player>()
	val enemies = ConcurrentHashMap<Int, Player>()

}