package org.abendigo.plugin

import java.util.*

object Plugins {

	private val nameToPlugin = HashMap<String, Plugin>()

	fun byName(pluginName: String) = nameToPlugin[pluginName]

	fun enable(plugin: Plugin) {
		nameToPlugin.putIfAbsent(plugin.name, plugin)
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