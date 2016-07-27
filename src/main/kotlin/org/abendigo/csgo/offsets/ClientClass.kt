package org.abendigo.csgo.offsets

import org.abendigo.DEBUG
import org.abendigo.csgo.csgo
import org.jire.arrowhead.get
import kotlin.LazyThreadSafetyMode.NONE

internal class ClientClass(val address: Int) {

	val classID by lazy<Int>(NONE) { csgo[address + 20] }

	val next by lazy<Int>(NONE) { csgo[address + 16] }

	val table by lazy<Int>(NONE) { csgo[address + 12] }

	fun readable() = try {
		csgo.read(address, 40, false).valid()
	} catch (t: Throwable) {
		if (DEBUG) t.printStackTrace()
		false
	}

}