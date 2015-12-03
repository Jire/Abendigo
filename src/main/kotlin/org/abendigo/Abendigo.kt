package org.abendigo

import co.paralleluniverse.fibers.Fiber
import co.paralleluniverse.kotlin.fiber
import co.paralleluniverse.strands.Strand
import java.util.concurrent.TimeUnit

fun sleep(duration: Int, timeUnit: TimeUnit = TimeUnit.MILLISECONDS) = sleep(duration.toLong(), timeUnit)

fun sleep(duration: Long, timeUnit: TimeUnit = TimeUnit.MILLISECONDS) = Strand.sleep(duration, timeUnit)

inline fun <T> every(duration: Int, timeUnit: TimeUnit = TimeUnit.MILLISECONDS, crossinline action: () -> T):
		Fiber<Unit> = every(duration.toLong(), timeUnit, action)

inline fun <T> every(duration: Long, timeUnit: TimeUnit = TimeUnit.MILLISECONDS, crossinline action: () -> T) =
		fiber {
			while (!Strand.interrupted()) {
				sleep(duration, timeUnit)
				action.invoke()
			}
		}

class UpdateableLazy<T>(private val lazy: () -> T) {

	private var current: T? = null
	private var previous: T? = null

	operator fun invoke(): T {
		if (current == null) unaryPlus()
		return current!!
	}

	operator fun unaryPlus(): T {
		previous = current
		current = lazy.invoke()
		return current!!
	}

	operator fun unaryMinus(): T {
		current = previous ?: return current!!
		return current!!
	}

}

fun <T> updateableLazy(lazy: () -> T) = UpdateableLazy(lazy)