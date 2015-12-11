package org.abendigo

import co.paralleluniverse.fibers.Suspendable
import org.abendigo.plugin.csgo.*
import java.lang.management.ManagementFactory
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

@Suspendable
@JvmOverloads fun sleep(duration: Long, timeUnit: TimeUnit = TimeUnit.MILLISECONDS) = Thread.sleep(duration)

@JvmOverloads fun sleep(duration: Int, timeUnit: TimeUnit = TimeUnit.MILLISECONDS) = sleep(duration.toLong(), timeUnit)

@JvmOverloads inline fun <T> every(duration: Long, timeUnit: TimeUnit = TimeUnit.MILLISECONDS,
                                   crossinline action: () -> T) =
		thread @Suspendable {
			do {
				action()
				sleep(duration, timeUnit)
			} while (!Thread.interrupted())
		}

@JvmOverloads fun <T> every(duration: Int, timeUnit: TimeUnit = TimeUnit.MILLISECONDS, action: () -> T):
		Thread = every(duration.toLong(), timeUnit, action)

open class UpdateableLazy<T>(private val lazy: () -> T) {

	@Volatile private var current: T? = null
	@Volatile private var previous: T? = null

	fun get(): T {
		if (current == null) +this
		return current!!
	}

	operator fun invoke() = get()

	fun update(): T {
		previous = current
		current = lazy()
		return current!!
	}

	operator fun unaryPlus() = update()

	fun rollback(): T {
		current = previous ?: return this()
		return current!!
	}

	operator fun unaryMinus() = rollback()

}

fun <T> updateableLazy(lazy: () -> T) = UpdateableLazy(lazy)

fun main(args: Array<String>) {
	println("Process ${ManagementFactory.getRuntimeMXBean().name}")
	BunnyHopPlugin().enable()
	RadarPlugin().enable()
	Thread.sleep(Long.MAX_VALUE)
}