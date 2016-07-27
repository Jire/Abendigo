package org.abendigo.csgo.offsets

import org.abendigo.csgo.csgo
import org.jire.arrowhead.get
import kotlin.LazyThreadSafetyMode.NONE

class RecvProp(val address: Int, val addressOffset: Int) {

	val table by lazy(NONE) { csgo.get<Int>(address + 0x28) }

	val name by lazy(NONE) {
		val bytes = ByteArray(64)
		csgo.read(csgo.int(address), bytes.size).read(0, bytes, 0, bytes.size)
		nvString(bytes)
	}

	val offset by lazy(NONE) { addressOffset + csgo.get<Int>(address + 0x2C) }

	val type by lazy<Int>(NONE) { csgo[address + 0x4] }

	val elements by lazy<Int>(NONE) { csgo[address + 0x34] }

	val stringBufferCount by lazy<Int>(NONE) { csgo[address + 0xC] }

}