package org.abendigo.cached

import java.util.concurrent.TimeUnit

open class Cached<T>(private val update: () -> T, private val set: ((T) -> Any)? = null) {

	@Volatile private var value: T? = null
	@Volatile private var previous: T? = null

	@Volatile private var updateStamp: Long = 0L

	@JvmName("get")
	operator fun invoke(): T = if (value != null) value!! else +this

	@JvmName("set")
	operator fun get(newValue: T) {
		if (set == null) throw UnsupportedOperationException()
		value = newValue // set first for deterministic behavior
		set.invoke(newValue)
	}

	@JvmName("update")
	operator fun unaryPlus(): T {
		previous = value
		value = update()
		updateStamp = System.nanoTime()
		return value!!
	}

	fun lastUpdate() = System.nanoTime() - updateStamp

	@JvmOverloads
	fun updatedSince(duration: Long, timeUnit: TimeUnit = TimeUnit.MILLISECONDS)
			= lastUpdate() >= timeUnit.toNanos(duration)

	@JvmOverloads @JvmName("updateIf")
	operator fun invoke(duration: Long, timeUnit: TimeUnit = TimeUnit.MILLISECONDS) = when {
		updatedSince(duration, timeUnit) -> this()
		else -> +this
	}

	override fun toString() = value.toString()
	override fun equals(other: Any?) = value!!.equals(other)
	override fun hashCode() = value!!.hashCode()

}

fun <T> cached(initializer: () -> T) = Cached(initializer)