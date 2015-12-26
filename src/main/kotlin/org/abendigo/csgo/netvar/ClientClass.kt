package org.abendigo.csgo.netvar

import org.abendigo.csgo.csgo

class ClientClass(val address: Int) {

	val classID by lazy { csgo.get<Int>(address + 20) }

	val next by lazy { csgo.get<Int>(address + 16) }

	val table by lazy { csgo.get<Int>(address + 12) }

	fun readable() = try {
		csgo.get(address, 40); true
	} catch (e: Exception) {
		false
	}

}