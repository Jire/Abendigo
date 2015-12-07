package org.abendigo.csgo

import org.abendigo.UpdateableLazy
import org.jire.kotmem.processes

val csgo = processes["csgo.exe"] // TODO make a system that supports CS:GO closing/not being opened yet

// modules
val client = csgo["client.dll"]
val engine = csgo["engine.dll"]

// constants
const val m_dwLocalPlayer = 0x00A9D44C
const val m_dwClientState = 0x005D3234
const val m_fFlags = 0x00000100
const val m_dwForceJump = 0x04AD805C

// objects

val clientState = UpdateableLazy() {
	ClientState(client[m_dwClientState])
}

object me : UpdateableLazy<Player>({ Player(client[m_dwLocalPlayer], 0) }) {
	val flags = UpdateableLazy<Int>() { csgo[this().address + m_fFlags] }
}