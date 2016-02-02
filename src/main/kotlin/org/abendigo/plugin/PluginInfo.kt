package org.abendigo.plugin

data class PluginInfo(val name: String, val version: String = "1.0",
                      val author: String = "", val description: String = "")