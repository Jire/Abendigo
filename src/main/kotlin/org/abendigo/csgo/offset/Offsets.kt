@file:JvmName("Offsets")

package org.abendigo.csgo.offset

import org.jire.kotmem.Module
import org.jire.kotmem.NativeBuffer

const val READ = 1
const val SUBTRACT = 2

fun offset(module: Module, patternOffset: Int, addressOffset: Int, flags: Int, values: ByteArray)
		= Offset(module, patternOffset, addressOffset, flags, values)

fun offset(module: Module, patternOffset: Int, addressOffset: Int, flags: Int, vararg values: Int)
		= offset(module, patternOffset, addressOffset, flags, toByteArray(*values))

fun offset(module: Module, patternOffset: Int, addressOffset: Int, flags: Int, value: Int)
		= offset(module, patternOffset, addressOffset, flags, toByteArray(value))

fun offset(module: Module, patternOffset: Int, addressOffset: Int, flags: Int, className: String)
		= offset(module, patternOffset, addressOffset, flags, className.toByteArray())

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