package org.abendigo.csgo.offsets

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap
import java.util.*
import kotlin.LazyThreadSafetyMode.NONE
import kotlin.reflect.KProperty

data class NetVarOffset(val className: String, val varName: String, val offset: Int) {

	override fun toString() = "$className $varName = 0x${Integer.toHexString(offset).toUpperCase()}"

}

val netVars by lazy(NONE) {
	val map = Int2ObjectArrayMap<NetVarOffset>(20000) // Have us covered for a while with 20K

	var clientClass = ClientClass(firstClass)
	while (clientClass.readable()) {
		val table = RecvTable(clientClass.table)
		if (!table.readable()) {
			clientClass = ClientClass(clientClass.next)
			continue
		}
		scanTable(map, table, 0, table.tableName)
		clientClass = ClientClass(clientClass.next)
	}

	// dumps to file
	/*val builder = StringBuilder()
	for ((hash, nv) in map) builder.append("$nv\n")
	Files.write(File("netvars.txt").toPath(), builder.toString().toByteArray())*/

	Collections.unmodifiableMap(map)
}

class NetVarDelegate(val className: String, var varName: String?, val offset: Int, val index: Int = -1) {

	operator fun getValue(thisRef: Any?, property: KProperty<*>): Int {
		if (varName == null) varName = property.name + if (index < 0) "" else "[$index]"
		return netVars[hashClassAndVar(className, varName!!)]!!.offset + offset
	}

}

fun netVar(className: String, varName: String? = null, offset: Int = 0, index: Int = -1)
		= NetVarDelegate(className, if (varName != null && index >= 0) "$varName[$index]" else varName, offset, index)

fun bpNetVar(varName: String? = null, offset: Int = 0, index: Int = -1)
		= netVar("DT_BasePlayer", varName, offset, index)

fun beNetVar(varName: String? = null, offset: Int = 0, index: Int = -1)
		= netVar("DT_BaseEntity", varName, offset, index)

fun cspNetVar(varName: String? = null, offset: Int = 0, index: Int = -1)
		= netVar("DT_CSPlayer", varName, offset, index)

fun bcwNetVar(varName: String? = null, offset: Int = 0, index: Int = -1)
		= netVar("DT_BaseCombatWeapon", varName, offset, index)

fun wepNetVar(varName: String? = null, offset: Int = 0, index: Int = -1)
		= netVar("DT_WeaponCSBase", varName, offset, index)

internal fun scanTable(netVars: MutableMap<Int, NetVarOffset>, table: RecvTable, offset: Int, name: String) {
	for (i in 0..table.propCount - 1) {
		val prop = RecvProp(table.propForId(i), offset)
		if (!Character.isDigit(prop.name[0])) {
			if (!prop.name.contains("baseclass")) {
				val netVar = NetVarOffset(name, prop.name, prop.offset)
				netVars.put(hashNetVar(netVar), netVar)
			}

			val child = prop.table
			if (child != 0) scanTable(netVars, RecvTable(child), prop.offset, name)
		}
	}
}

internal fun nvString(bytes: ByteArray): String {
	for (i in 0..bytes.size - 1) if (bytes[i].toInt() == 0) bytes[i] = 32
	return String(bytes).split(" ")[0].trim()
}

private fun hashClassAndVar(className: String, varName: String) = className.hashCode() xor varName.hashCode()

private fun hashNetVar(netVar: NetVarOffset) = hashClassAndVar(netVar.className, netVar.varName)