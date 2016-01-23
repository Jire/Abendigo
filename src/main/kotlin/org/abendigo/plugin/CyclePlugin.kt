package org.abendigo.plugin

import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.MILLISECONDS

abstract class CyclePlugin(name: String, version: String = "1.0", author: String = "",
                           description: String = "", val duration: Int,
                           val durationUnit: TimeUnit = MILLISECONDS) : Plugin(name, version, author, description) {

	override fun enable() = every(duration, durationUnit) {
		cycle()
	}

	abstract fun cycle()

}