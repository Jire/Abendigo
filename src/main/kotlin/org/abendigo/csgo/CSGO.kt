@file:JvmName("CSGO")

package org.abendigo.csgo

import org.abendigo.Addressable
import org.abendigo.cached.*
import org.abendigo.csgo.offsets.*
import org.jire.kotmem.Module
import org.jire.kotmem.Processes
import java.util.concurrent.ConcurrentHashMap

inline fun <reified T : Any> cached(address: Int, offset: Int = 0)
		= Cached<T>({ csgo.get(address + offset) }, { csgo.set(address, it) })

inline fun <reified T : Any> cached(address: Long, offset: Int = 0): Cached<T> = cached(address.toInt(), offset)

inline fun <reified T : Any> cached(module: Module, offset: Int = 0)
		= Cached<T>({ module.get(offset) }, { module.set(offset, it) })

inline fun <reified T : Any> Addressable.cached(offset: Int = 0): Cached<T> = cached(this.address, offset)

val csgo by lazy { Processes["csgo.exe"] } // TODO make a system that supports CS:GO closing/not being opened yet

val client by lazy { csgo["client.dll"] }
val engine by lazy { csgo["engine.dll"] }

const val ENTITY_SIZE = 16
const val GLOW_OBJECT_SIZE = 56

// objects

val clientState = cached { ClientState(engine.get(m_dwClientState)) }

val glowObject = cached<Int>(client, m_dwGlowObject)
val glowObjectCount = cached<Int>(client, m_dwGlowObject + 4)

object Me : Cached<Player>({
	val address: Int = client.get(m_dwLocalPlayer)
	val index = /*client.get<Int>(address + m_dwIndex) - 1*/0 // TODO: can use me-specific index offset
	Player(address, index)
}) {
	@JvmStatic val flags = cached<Int>(this().address, m_fFlags)
	@JvmStatic val crosshairID = cached { csgo.get<Int>(this().address + m_iCrossHairID) - 1 }
	@JvmStatic val targetAddress = cached {
		val crosshairID = +crosshairID
		if (crosshairID > /*=*/ 0) client.get(m_dwEntityList + (crosshairID * ENTITY_SIZE)) else -1
	}
}

val entities = initializedCache({ ConcurrentHashMap<Int, Entity>(64) }) {
	players.clear()
	team.clear()
	enemies.clear()
	val myTeam = +Me().team
	for (i in 0..+glowObjectCount - 1) {
		val address: Int = client.get(m_dwEntityList + (i * ENTITY_SIZE))
		if (Me().address != address && address > 0) {
			val entity = Entity(address, i)
			put(i, entity)
			val entityTeam: Int = csgo.get(address + m_iTeamNum)
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