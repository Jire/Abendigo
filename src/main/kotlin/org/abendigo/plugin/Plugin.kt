package org.abendigo.plugin

abstract class Plugin(val name: String) {

	open val version: String = "1.0"
	open val author: String = ""
	open val description: String = ""

	abstract fun enable()

	open fun disable() {
	}

}

fun enable(plugin: Plugin) = plugin.enable()