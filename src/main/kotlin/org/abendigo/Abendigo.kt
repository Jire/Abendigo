package org.abendigo

import co.paralleluniverse.fibers.*
import co.paralleluniverse.kotlin.fiber
import co.paralleluniverse.strands.Strand
import org.abendigo.plugin.csgo.*
import org.jire.kotmem.isKeyDown
import java.lang.management.ManagementFactory
import java.util.concurrent.TimeUnit

@Suspendable
fun sleep(duration: Long, timeUnit: TimeUnit = TimeUnit.MILLISECONDS) = Strand.sleep(duration, timeUnit)

fun sleep(duration: Int, timeUnit: TimeUnit = TimeUnit.MILLISECONDS) = sleep(duration.toLong(), timeUnit)

fun <T> every(duration: Int, timeUnit: TimeUnit = TimeUnit.MILLISECONDS, action: () -> T):
		Fiber<Unit> = every(duration.toLong(), timeUnit, action)

inline fun <T> every(duration: Long, timeUnit: TimeUnit = TimeUnit.MILLISECONDS, crossinline action: () -> T) =
		fiber @Suspendable {
			do {
				action()
				sleep(duration, timeUnit)
			} while (!Strand.interrupted())
		}

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

object keys {
	@JvmStatic operator fun get(vKey: Int) = isKeyDown(vKey)
}

fun main(args: Array<String>) {
	println("Process@${ManagementFactory.getRuntimeMXBean().name}")
	BunnyHopPlugin().enable()
	RadarPlugin().enable()
	Thread.sleep(Long.MAX_VALUE)
}