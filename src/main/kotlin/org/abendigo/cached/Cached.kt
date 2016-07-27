package org.abendigo.cached

import java.lang.System.nanoTime
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.MILLISECONDS
import java.util.concurrent.atomic.AtomicLong

open class Cached<T>(private val update: () -> T) {

	@Volatile private var value: T? = null
	@Volatile private var previous: T? = null

	private val updateStamp = AtomicLong(0)

	@JvmName("get")
	operator fun invoke(): T = if (value != null) value!! else +this

	@JvmName("set")
	operator fun get(newValue: T) {
		value = newValue // set first for deterministic behavior
	}

	@JvmName("update")
	operator fun unaryPlus(): T {
		previous = value
		value = update()
		updateStamp.set(nanoTime())
		return value!!
	}

	fun lastUpdate() = nanoTime() - updateStamp.get()

	@JvmOverloads
	fun updatedSince(duration: Long, timeUnit: TimeUnit = MILLISECONDS)
			= lastUpdate() >= timeUnit.toNanos(if (duration > 1) duration - 1 else duration)

	@JvmOverloads @JvmName("updateIf")
	operator fun invoke(duration: Long, timeUnit: TimeUnit = MILLISECONDS) = when {
		updatedSince(duration, timeUnit) -> this()
		else -> +this
	}

	@JvmOverloads @JvmName("updateIf")
	operator fun invoke(duration: Int, timeUnit: TimeUnit = MILLISECONDS) = invoke(duration.toLong(), timeUnit)

	override fun toString() = value.toString()
	override fun equals(other: Any?) = value!!.equals(other)
	override fun hashCode() = value!!.hashCode()

}

fun <T> cached(initializer: () -> T) = Cached(initializer)