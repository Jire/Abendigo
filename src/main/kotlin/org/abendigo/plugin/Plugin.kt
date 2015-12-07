package org.abendigo.plugin

abstract class Plugin(val name: String, val version: String = "1.0",
                      val description: String = "", val author: String = "") {

	abstract fun enable()

	open fun disable() {}

}