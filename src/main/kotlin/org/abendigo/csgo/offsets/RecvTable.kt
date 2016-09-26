package org.abendigo.csgo.offsets

import org.abendigo.DEBUG
import org.abendigo.csgo.csgo
import org.jire.arrowhead.get
import kotlin.LazyThreadSafetyMode.NONE

class RecvTable(val address: Int, val offset: Int = 16) {

	fun propForId(id: Int) = csgo.get<Int>(address) + id * 60

	val tableName by lazy(NONE) {
		val bytes = ByteArray(32)
		csgo.read(csgo.int(address + 12), bytes.size, false)!!.read(0, bytes, 0, bytes.size)
		nvString(bytes)
	}

	val propCount by lazy(NONE) { csgo.get<Int>(address + 4) }

	fun readable() = csgo.read(address, offset, false) != null

}