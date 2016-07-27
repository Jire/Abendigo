package org.abendigo.csgo.offsets

import com.sun.jna.Memory
import org.jire.arrowhead.Module
import org.jire.arrowhead.get
import kotlin.LazyThreadSafetyMode.NONE
import kotlin.reflect.KProperty

const val READ = 1
const val SUBTRACT = 2

data class Offset(val module: Module, val patternOffset: Int, val addressOffset: Int,
                  val flags: Int, val values: ByteArray) {

	val memory = module.process.read(module.address, module.size.toInt(), false)

	val address by lazy(NONE) {
		val off = module.size - values.size
		var i = 0L
		while (i < off) {
			if (checkMask(memory, i.toInt(), values)) {
				i += module.address + patternOffset
				if ((flags and READ) == READ) i = module.process.get<Int>(i).toLong()
				if ((flags and SUBTRACT) == SUBTRACT) i -= module.address
				val result = (i + addressOffset).toInt()
				return@lazy result
			}
			i++
		}

		throw IllegalStateException("Failed to resolve offset")
	}

	operator fun getValue(thisRef: Any?, property: KProperty<*>) = try {
		address
	} catch (t: Throwable) {
		throw IllegalStateException("${t.message} of property ${property.name}")
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

internal fun checkMask(data: Memory, offset: Int, pMask: ByteArray): Boolean {
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