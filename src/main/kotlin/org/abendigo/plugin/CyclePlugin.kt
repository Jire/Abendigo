package org.abendigo.plugin

import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.MILLISECONDS

abstract class CyclePlugin(name: String, val duration: Int, val durationUnit: TimeUnit = MILLISECONDS) : Plugin(name) {

	override fun enable() = every(duration, durationUnit) {
		cycle()
	}

	abstract fun cycle()

}