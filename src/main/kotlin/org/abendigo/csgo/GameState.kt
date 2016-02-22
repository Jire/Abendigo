package org.abendigo.csgo

import org.abendigo.csgo.GameState.NONE

enum class GameState(val id: Int) {

	NONE(0),
	CHALLENGE(1),
	CONNECTING(2),
	ENTERING(3),
	PRESPAWN(4),
	SPAWNING(5),
	PLAYING(6),
	CHANGING_LEVEL(7)

}

private val gameStates = GameState.values()

fun gameState(id: Int) = if (id <= gameStates.size) gameStates[id] else NONE