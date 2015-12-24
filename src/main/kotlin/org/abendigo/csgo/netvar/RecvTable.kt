package org.abendigo.csgo.netvar

import com.sun.jna.platform.win32.Win32Exception
import org.abendigo.csgo.csgo

internal class RecvTable(val address: Int, val offset: Int = 0x10) {

	val readable by lazy {
		try {
			csgo.get(address, offset); true
		} catch (e: Win32Exception) {
			false
		}
	}

	val tableName by lazy {
		val bytes = ByteArray(32)
		csgo.get(csgo.get<Int>(address + 0xC), bytes.size).get(bytes)
		nvString(bytes)
	}

	val propCount by lazy<Int> { csgo.get(address + 0x4) }

	fun propForId(id: Int) = csgo.get<Int>(address) + (id * 0x3C)

}