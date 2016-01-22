@file:JvmName("CSGO")

package org.abendigo.csgo

import org.abendigo.Addressable
import org.abendigo.cached.Cached
import org.jire.kotmem.Module
import org.jire.kotmem.Processes

val csgo by lazy { Processes["csgo.exe"] } // TODO make a system that supports CS:GO closing/not being opened yet

const val ENTITY_SIZE = 16
const val GLOW_OBJECT_SIZE = 56

inline fun <reified T : Any> cached(address: Int, offset: Int = 0)
		= Cached<T>({ csgo.get(address + offset) }, { csgo.set(address, it) })

inline fun <reified T : Any> cached(address: Long, offset: Int = 0): Cached<T> = cached(address.toInt(), offset)

inline fun <reified T : Any> cached(module: Module, offset: Int = 0)
		= Cached<T>({ module.get(offset) }, { module.set(offset, it) })

inline fun <reified T : Any> Addressable.cached(offset: Int = 0): Cached<T> = cached(this.address, offset)