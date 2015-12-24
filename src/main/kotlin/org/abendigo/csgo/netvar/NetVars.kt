package org.abendigo.csgo.netvar

import org.abendigo.csgo.client
import org.abendigo.csgo.offset.READ
import org.abendigo.csgo.offset.offset
import java.util.*
import kotlin.reflect.KProperty

private val netVars by lazy {
	val map = HashMap<Int, NetVar>(16500)
	var clientClass = ClientClass(firstClass)
	while (clientClass.readable) {
		clientClass = ClientClass(clientClass.next)
		val table = RecvTable(clientClass.table)
		if (table.readable) scanTable(map, table, 0, table.tableName)
	}
	/* yield */ Collections.unmodifiableMap(map)
}

class NetVarDelegate(val className: String, var varName: String?, val offset: Int) {
	operator fun getValue(thisRef: Any?, property: KProperty<*>): Int {
		if (varName == null) varName = property.name
		println(netVars.size)
		return netVars[hashClassAndVar(className, varName!!)]!!.offset + offset
	}
}

fun netVar(className: String, varName: String? = null, offset: Int = 0, index: Int = -1)
		= NetVarDelegate(className, if (index >= 0) "$varName[$index]" else varName, offset)

fun bpNetVar(varName: String? = null, offset: Int = 0, index: Int = -1)
		= netVar("DT_BasePlayer", varName, offset, index)

fun beNetVar(varName: String? = null, offset: Int = 0, index: Int = -1)
		= netVar("DT_BaseEntity", varName, offset, index)

private val worldDecal by offset(client, 0, 0, 0, "DT_TEWorldDecal")
private val firstClass by offset(client, 0x2B, 0, READ, worldDecal)

internal fun scanTable(netVars: HashMap<Int, NetVar>, table: RecvTable, offset: Int, name: String) {
	for (i in 0..table.propCount) {
		val prop = RecvProp(table.propForId(i), offset)

		if (!Character.isDigit(prop.name[0])) {
			if (!prop.name.contains("baseclass")) {
				val netVar = NetVar(name, prop.name, prop.offset)
				netVars.put(hashClassAndVar(netVar.className, netVar.varName), netVar)
			}

			val child = prop.table
			if (child != 0)
				scanTable(netVars, RecvTable(child), prop.offset, name)
		}
	}
}

internal fun nvString(bytes: ByteArray): String {
	for (i in 0..bytes.size - 1) if (bytes[i].toInt() == 0) bytes[i] = 32
	return String(bytes).split(" ")[0].trim()
}

private fun hashClassAndVar(className: String, varName: String) = className.hashCode() xor varName.hashCode()