package org.abendigo.plugin

import java.util.*

object Plugins {

	val map = HashMap<String, Plugin>()

	fun byName(pluginName: String) = map[pluginName]

	fun enable(plugin: Plugin) {
		map.put(plugin.name, plugin)
		plugin.enable()
		plugin.enabled = true
	}

	fun enable(pluginName: String) = enable(byName(pluginName)!!)

	fun disable(plugin: Plugin) {
		plugin.disable()
		plugin.enabled = false
	}

	fun disable(pluginName: String) = disable(byName(pluginName)!!)

}