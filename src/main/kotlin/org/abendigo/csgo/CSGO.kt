package org.abendigo.csgo

import org.abendigo.*
import org.jire.kotmem.processes

val csgo = processes.get("csgo.exe") // TODO make a system that supports CS:GO closing/not being opened yet

// modules
val client = csgo.get("client.dll")
val engine = csgo.get("engine.dll")

// constants

const val ENTITY_SIZE = 16

const val m_dwLocalPlayer = 0x00A6A444
const val m_dwClientState = 0x006062C4
const val m_fFlags = 0x00000100
const val m_dwForceJump = 0x04AED2D0
const val m_dwGlowObject = 0x04B6DA1C
const val m_dwRadarBase = 0x04A8D43C
const val m_bSpotted = 0x00000935
const val m_dwEntityList = 0x04A58794
const val m_iTeamNum = 0x000000F0

// objects

val clientState = updateableLazy { ClientState(client.get(m_dwClientState)) }

val objectCount = updateableLazy { client.get<Int>(m_dwGlowObject + 4) }

object me : UpdateableLazy<Player>({ Player(client.get(m_dwLocalPlayer), 0) }) {
	@JvmStatic val flags = updateableLazy<Int> { csgo.get(this().address + m_fFlags) }
}