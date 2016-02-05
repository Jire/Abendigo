package org.abendigo.csgo

import org.abendigo.cached.initializedCache
import org.abendigo.csgo.offsets.*
import java.util.concurrent.ConcurrentHashMap

val clientDLL by lazy { csgo["client.dll"] }

object Client {

	val glowObject = cached<Int>(clientDLL, m_dwGlowObject)

	val glowObjectCount = cached<Int>(clientDLL, m_dwGlowObject + 4)

	val entities = initializedCache({ ConcurrentHashMap<Int, Entity>(64) }) {
		players.clear()
		team.clear()
		enemies.clear()
		val myTeam = +Me().team
		for (i in 0..+glowObjectCount - 1) {
			val address: Int = clientDLL[m_dwEntityList + (i * ENTITY_SIZE)]
			if (Me().address != address && address > 0) {
				val entity = Entity(address, i)
				put(i, entity)
				val entityTeam: Int = csgo[address + m_iTeamNum]
				if (entityTeam == 2 || entityTeam == 3) {
					// TODO check via CS:GO class ID
					val player = Player(entity)
					if (myTeam == entityTeam) team.put(i, player)
					else enemies.put(i, player)
				}
			}
		}
		players.putAll(team)
		players.putAll(enemies)
	}

	val players = ConcurrentHashMap<Int, Player>()
	val team = ConcurrentHashMap<Int, Player>()
	val enemies = ConcurrentHashMap<Int, Player>()

}