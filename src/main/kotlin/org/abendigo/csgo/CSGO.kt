@file:JvmName("CSGO")

package org.abendigo.csgo

import org.abendigo.Addressable
import org.abendigo.cached.Cached
import org.jire.arrowhead.Module
import org.jire.arrowhead.get
import org.jire.arrowhead.processByName

val csgo by lazy(LazyThreadSafetyMode.NONE) { processByName("csgo.exe")!! }
val csgoModule by lazy(LazyThreadSafetyMode.NONE) { csgo.modules["csgo.exe"]!! }

const val ENTITY_SIZE = 16
const val GLOW_OBJECT_SIZE = 56

inline fun <reified T : Any> cached(address: Int, offset: Int = 0)
		= Cached<T>({ csgo[address + offset] })

inline fun <reified T : Any> cached(address: Long, offset: Int = 0): Cached<T> = cached(address.toInt(), offset)

inline fun <reified T : Any> cached(module: Module, offset: Int = 0)
		= Cached({ module.get<T>(offset) })

inline fun <reified T : Any> Addressable.cached(offset: Int = 0): Cached<T> = cached(this.address, offset)