package org.abendigo.csgo.netvar

import org.abendigo.csgo.csgo

class RecvTable(val address: Int, val offset: Int = 16) {

	fun propForId(id: Int) = csgo.get<Int>(address) + id * 60

	val tableName by lazy {
		val bytes = ByteArray(32)
		csgo.get(csgo.get<Int>(address + 12), bytes.size).get(bytes)
		nvString(bytes)
	}

	val propCount by lazy { csgo.get<Int>(address + 4) }

	fun readable() = try {
		csgo.getRaw(address, offset); true
	} catch (e: Exception) {
		false
	}

}