package org.abendigo.csgo

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