package org.abendigo.plugin

abstract class Plugin(val name: String, val version: String = "1.0",
                      val author: String = "", val description: String = "") {

	abstract fun enable()

	open fun disable() {}

}