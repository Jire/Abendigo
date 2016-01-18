@file:JvmName("Abendigo")

package org.abendigo

import org.abendigo.plugin.csgo.*
import java.lang.management.ManagementFactory
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

open class UpdateableLazy<T>(private val update: () -> T) {

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
		value = update()
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

fun <T> updateableLazy(initializer: () -> T) = UpdateableLazy(initializer)

class ObjectUpdateableLazy<T>(init: () -> T, update: T.() -> Any, private val initialized: T = init()) : UpdateableLazy<T>({
	update(initialized)
	initialized
}) {}

fun <T> objectUpdateableLazy(initializer: () -> T, updater: T.() -> Any) = ObjectUpdateableLazy(initializer, updater)

fun main(args: Array<String>) {
	println("Process ${ManagementFactory.getRuntimeMXBean().name}")

	// TODO make a proper plugin system
	BunnyHopPlugin().enable()
	RadarPlugin().enable()
	ESPPlugin().enable()
	// AimAssistPlugin().enable()
}