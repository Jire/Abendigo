package org.abendigo.plugin

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
import java.util.*

object Plugins {

	val map: MutableMap<String, Plugin> = Collections.synchronizedMap(Object2ObjectArrayMap<String, Plugin>())

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