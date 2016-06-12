@file:JvmName("Every")

package org.abendigo.plugin

import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.MILLISECONDS
import kotlin.concurrent.thread

@JvmOverloads
fun sleep(duration: Long, timeUnit: TimeUnit = MILLISECONDS) = Thread.sleep(timeUnit.toMillis(duration))

@JvmOverloads
fun sleep(duration: Int, timeUnit: TimeUnit = MILLISECONDS) = sleep(duration.toLong(), timeUnit)

@JvmOverloads
inline fun <T> every(duration: Long, timeUnit: TimeUnit = MILLISECONDS, crossinline action: () -> T) {
	thread {
		do {
			try {
				action()
			} catch (t: Throwable) {
				t.printStackTrace()
			}
			sleep(duration, timeUnit)
		} while (!Thread.interrupted())
	}
}

@JvmOverloads
inline fun <T> every(duration: Int, timeUnit: TimeUnit = MILLISECONDS, crossinline action: () -> T): Unit
		= every(duration.toLong(), timeUnit, action)