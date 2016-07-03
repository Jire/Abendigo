@file:JvmName("Every")

package org.abendigo.plugin

import org.abendigo.DEBUG
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.MILLISECONDS
import kotlin.concurrent.thread

@JvmOverloads
fun sleep(duration: Long, timeUnit: TimeUnit = MILLISECONDS) {
	var ns = timeUnit.toNanos(duration)
	val ms = ns / 1000000
	ns -= (ms * 1000000)
	Thread.sleep(ms, ns.toInt())
}

@JvmOverloads
fun sleep(duration: Int, timeUnit: TimeUnit = MILLISECONDS) = sleep(duration.toLong(), timeUnit)

@JvmOverloads
inline fun <T> every(duration: Long, timeUnit: TimeUnit = MILLISECONDS, crossinline action: () -> T) {
	thread {
		do {
			try {
				action()
			} catch (t: Throwable) {
				if (DEBUG) t.printStackTrace()
			}
			sleep(duration, timeUnit)
		} while (!Thread.interrupted())
	}
}

@JvmOverloads
inline fun <T> every(duration: Int, timeUnit: TimeUnit = MILLISECONDS, crossinline action: () -> T): Unit
		= every(duration.toLong(), timeUnit, action)