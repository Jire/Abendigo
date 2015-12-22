@file:JvmName("Offsets")

package org.abendigo.csgo

import org.jire.kotmem.Module
import java.nio.ByteBuffer
import kotlin.reflect.KProperty

internal const val READ = 1
internal const val SUBTRACT = 2

private var lastModule: Module? = null
private var moduleData: ByteBuffer? = null

class Offset(val module: Module, val patternOffset: Int, val addressOffset: Int,
             val flags: Int, val values: ByteArray) {

	private var address: Int? = null

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
				i += module.address + patternOffset
				if ((flags and READ) == READ) i = module.process.get<Int>(i).toLong()
				if ((flags and SUBTRACT) == SUBTRACT) i -= module.address
				address = (i + addressOffset).toInt()
				return address!!
			}
			i++
		}

		throw IllegalStateException("Failed to resolve offset \"${property.name}\"")
	}

}

fun offset(module: Module, patternOffset: Int, addressOffset: Int, flags: Int, values: ByteArray)
		= Offset(module, patternOffset, addressOffset, flags, values)

fun offset(module: Module, patternOffset: Int, addressOffset: Int, flags: Int, vararg values: Int)
		= offset(module, patternOffset, addressOffset, flags, toByteArray(*values))

fun offset(module: Module, patternOffset: Int, addressOffset: Int, flags: Int, value: Int)
		= offset(module, patternOffset, addressOffset, flags, toByteArray(value))

fun offset(module: Module, patternOffset: Int, addressOffset: Int, flags: Int, className: String)
		= offset(module, patternOffset, addressOffset, flags, className.toByteArray())

private fun checkMask(data: ByteBuffer, offset: Int, pMask: ByteArray): Boolean {
	for (i in pMask.indices)
		if (pMask[i].toInt() != 0 && (pMask[i] != data.get(offset + i)))
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