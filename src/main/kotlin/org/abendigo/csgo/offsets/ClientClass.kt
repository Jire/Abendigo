package org.abendigo.csgo.offsets

import org.abendigo.csgo.csgo

internal class ClientClass(val address: Int) {

	val classID by lazy<Int> { csgo[address + 20] }

	val next by lazy<Int> { csgo[address + 16] }

	val table by lazy<Int> { csgo[address + 12] }

	fun readable() = try {
		csgo[address, 40]; true
	} catch (e: Exception) {
		false
	}

}