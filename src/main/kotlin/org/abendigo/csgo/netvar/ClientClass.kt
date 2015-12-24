package org.abendigo.csgo.netvar

import com.sun.jna.platform.win32.Win32Exception
import org.abendigo.csgo.csgo

internal class ClientClass(val address: Int) {

	val classID by lazy { csgo.get<Int>(address + 0x14) }

	val next by lazy { csgo.get<Int>(address + 0x10) }

	val table by lazy { csgo.get<Int>(address + 0xC) }

	val readable by lazy {
		try {
			csgo.get(address, 0x28); true
		} catch (e: Win32Exception) {
			false
		}
	}

}