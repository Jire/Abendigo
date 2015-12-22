package org.abendigo.csgo

data class NetVar(val className: String, val varName: String, val offset: Int) {
	override fun toString() = "$className $varName = 0x${Integer.toHexString(offset).toUpperCase()}"
}