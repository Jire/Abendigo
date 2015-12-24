@file:JvmName("Abendigo")

package org.abendigo

import org.abendigo.csgo.entities
import org.abendigo.plugin.csgo.*
import java.lang.management.ManagementFactory
import java.net.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

@JvmOverloads
fun sleep(duration: Long, timeUnit: TimeUnit = TimeUnit.MILLISECONDS) = Thread.sleep(timeUnit.toMillis(duration))

@JvmOverloads
fun sleep(duration: Int, timeUnit: TimeUnit = TimeUnit.MILLISECONDS) = sleep(duration.toLong(), timeUnit)

@JvmOverloads
inline fun <T> every(duration: Long, timeUnit: TimeUnit = TimeUnit.MILLISECONDS, crossinline action: () -> T) {
	thread {
		do {
			action()
			sleep(duration, timeUnit)
		} while (!Thread.interrupted())
	}
}

@JvmOverloads
inline fun <T> every(duration: Int, timeUnit: TimeUnit = TimeUnit.MILLISECONDS, crossinline action: () -> T): Unit
		= every(duration.toLong(), timeUnit, action)

open class UpdateableLazy<T>(private val lazy: () -> T) {

	@Volatile private var value: T? = null
	@Volatile private var previous: T? = null

	@Volatile private var updateStamp: Long = 0L

	fun get(): T {
		if (value == null) +this
		return value!!
	}

	operator fun invoke() = get()

	fun update(): T {
		previous = value
		value = lazy()
		updateStamp = System.nanoTime()
		return value!!
	}

	operator fun unaryPlus() = update()

	fun lastUpdate() = System.nanoTime() - updateStamp

	@JvmOverloads
	fun updatedSince(duration: Long, timeUnit: TimeUnit = TimeUnit.MILLISECONDS) = lastUpdate() >= timeUnit.toNanos(duration)

	@JvmOverloads
	fun updateIf(duration: Long, timeUnit: TimeUnit = TimeUnit.MILLISECONDS) = when {
		updatedSince(duration, timeUnit) -> this()
		else -> +this
	}

	operator fun invoke(duration: Long, timeUnit: TimeUnit = TimeUnit.MILLISECONDS) = updateIf(duration, timeUnit)

	override fun toString() = value.toString()
	override fun equals(other: Any?) = value!!.equals(other)
	override fun hashCode() = value!!.hashCode()

}

fun <T> updateableLazy(lazy: () -> T) = UpdateableLazy(lazy)

fun main(args: Array<String>) {
	println("Process ${ManagementFactory.getRuntimeMXBean().name}")

	every(8, TimeUnit.SECONDS) { +entities } // updates all entities so plugins don't need to

	// TODO make a proper plugin system
	BunnyHopPlugin().enable()
	RadarPlugin().enable()
	ESPPlugin().enable()
	// AimAssistPlugin().enable()
}