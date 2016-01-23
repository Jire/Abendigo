package org.abendigo.csgo.offsets

import org.jire.kotmem.Module
import org.jire.kotmem.NativeBuffer
import kotlin.reflect.KProperty

const val READ = 1
const val SUBTRACT = 2

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

fun scanOffset(module: Module, patternOffset: Int, addressOffset: Int, flags: Int, values: ByteArray)
		= Offset(module, patternOffset, addressOffset, flags, values)

fun scanOffset(module: Module, patternOffset: Int, addressOffset: Int, flags: Int, vararg values: Int)
		= scanOffset(module, patternOffset, addressOffset, flags, toByteArray(*values))

fun scanOffset(module: Module, patternOffset: Int, addressOffset: Int, flags: Int, value: Int)
		= scanOffset(module, patternOffset, addressOffset, flags, toByteArray(value))

fun scanOffset(module: Module, patternOffset: Int, addressOffset: Int, flags: Int, className: String)
		= scanOffset(module, patternOffset, addressOffset, flags, className.toByteArray())

internal fun checkMask(data: NativeBuffer, offset: Int, pMask: ByteArray): Boolean {
	for (i in pMask.indices)
		if (pMask[i].toInt() != 0 && (pMask[i] != data.getByte((offset + i).toLong())))
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