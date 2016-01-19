package org.abendigo.csgo.offset

import org.jire.kotmem.Module
import kotlin.reflect.KProperty

data class Offset(val module: Module, val patternOffset: Int, val addressOffset: Int,
                  val flags: Int, val values: ByteArray) {

	val address by lazy {
		val off = module.size - values.size
		var i = 0L
		while (i < off) {
			if (checkMask(module.buffer, i.toInt(), values)) {
				i += module.address + patternOffset
				if ((flags and READ) == READ) i = module.process.get<Int>(i).toLong()
				if ((flags and SUBTRACT) == SUBTRACT) i -= module.address
				return@lazy (i + addressOffset).toInt()
			}
			i++
		}

		throw IllegalStateException("Failed to resolve offset")
	}

	operator fun getValue(thisRef: Any?, property: KProperty<*>) = try {
		address
	} catch (ise: IllegalStateException) {
		throw IllegalStateException("${ise.message} of property ${property.name}")
	}

}