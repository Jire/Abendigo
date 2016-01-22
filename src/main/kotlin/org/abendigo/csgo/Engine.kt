package org.abendigo.csgo

import org.abendigo.cached.cached
import org.abendigo.csgo.offsets.m_dwClientState
import org.abendigo.csgo.offsets.m_dwInGame

val engineDLL by lazy { csgo["engine.dll"] }

object Engine {

	val clientState = cached { ClientState(engineDLL.get(m_dwClientState)) }

	val inGame = cached<Int>(engineDLL, m_dwInGame)

}