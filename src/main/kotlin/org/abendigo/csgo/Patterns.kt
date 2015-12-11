package org.abendigo.csgo

import org.jire.kotmem.Module
import java.nio.ByteBuffer
import kotlin.reflect.KProperty

internal const val READ = 1
internal const val SUBTRACT = 2

private var lastModule: Module? = null
private var moduleData: ByteBuffer? = null

class pattern(val module: Module, val pattern_offset: Int, val address_offset: Int,
              val flags: Int, val values: ByteArray) {

	private var address: Int? = null

	constructor(module: Module, pattern_offset: Int, address_offset: Int, flags: Int, vararg values: Int) :
	this(module, pattern_offset, address_offset, flags, toByteArray(*values))

	constructor(module: Module, pattern_offset: Int, address_offset: Int, flags: Int, value: Int) :
	this(module, pattern_offset, address_offset, flags, toByteArray(value))

	constructor(module: Module, pattern_offset: Int, address_offset: Int, flags: Int, className: String) :
	this(module, pattern_offset, address_offset, flags, className.toByteArray())

	operator fun getValue(thisRef: Any?, property: KProperty<*>): Int {
		if (address != null) return address!!

		if (lastModule == null || !lastModule!!.equals(module)) {
			moduleData = module.process.get(module.pointer, module.size)
			lastModule = module
		}

		val off = module.size - values.size
		var i = 0L
		while (i < off) {
			if (checkMask(moduleData!!, i.toInt(), values)) {
				i += module.address + pattern_offset
				if ((flags and READ) == READ) i = module.process.get<Int>(i).toLong()
				if ((flags and SUBTRACT) == SUBTRACT) i -= module.address
				address = (i + address_offset).toInt()
				return address!!
			}
			i++
		}
		throw IllegalStateException("Failed to scan offset \"${property.name}\"")
	}

}

private fun checkMask(data: ByteBuffer, offset: Int, pMask: ByteArray): Boolean {
	for (i in pMask.indices) if (pMask[i].toInt() != 0 && (pMask[i] != data.get(offset + i)))
		return false
	return true
}

private fun toByteArray(value: Int) = byteArrayOf(value.toByte(), (value shr 8).toByte(),
		(value shr 16).toByte(), (value shr 24).toByte())

private fun toByteArray(vararg value: Int): ByteArray {
	val array = ByteArray(value.size)
	for (i in value.indices) array[i] = value[i].toByte()
	return array
}