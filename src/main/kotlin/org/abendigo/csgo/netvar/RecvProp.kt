package org.abendigo.csgo.netvar

import org.abendigo.csgo.csgo

class RecvProp(val address: Int, val addressOffset: Int) {

	val table by lazy { csgo.get<Int>(address + 0x28) }

	val name by lazy {
		val bytes = ByteArray(64)
		csgo.get(csgo.get<Int>(address), bytes.size).bytes(bytes)
		nvString(bytes)
	}

	val offset by lazy { addressOffset + csgo.get<Int>(address + 0x2C) }

	val type by lazy<Int> { csgo.get(address + 0x4) }

	val elements by lazy<Int> { csgo.get(address + 0x34) }

	val stringBufferCount by lazy<Int> { csgo.get(address + 0xC) }

}