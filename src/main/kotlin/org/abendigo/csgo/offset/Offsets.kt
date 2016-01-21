@file:JvmName("Offsets")

package org.abendigo.csgo.offset

import org.abendigo.csgo.client
import org.abendigo.csgo.engine

val worldDecal by offset(client, 0, 0, 0, "DT_TEWorldDecal")
val firstClass by offset(client, 0x2B, 0, READ, worldDecal)

// client.dll offsets
val m_dwRadarBase by offset(client, 1, 0, READ or SUBTRACT, 161, 0, 0, 0, 0, 139, 12, 176, 139, 1, 255, 80, 0,
		70, 59, 53, 0, 0, 0, 0, 124, 234, 139, 13, 0, 0, 0, 0)
val m_dwWeaponTable by offset(client, 1, 0, READ or SUBTRACT,
		161, 0, 0, 0, 0, 15, 183, 201, 3, 201, 139, 68, 0, 12, 195)
val m_dwWeaponTableIndex by offset(client, 3, 0, READ, 102, 139, 142, 0, 0, 0, 0, 232, 0, 0, 0, 0, 5, 0, 0, 0, 0, 80)
val m_dwInput by offset(client, 1, 0, READ or SUBTRACT, 185, 0, 0, 0, 0, 255, 117, 8, 232, 0, 0, 0, 0, 139, 6)
val m_dwGlowObject by offset(client, 1, 4, READ or SUBTRACT, 161, 0, 0, 0, 0, 168, 1, 117, 0, 15, 87, 192, 199, 5)
val m_dwForceJump by offset(client, 2, 0, READ or SUBTRACT,
		137, 21, 0, 0, 0, 0, 139, 21, 0, 0, 0, 0, 246, 194, 3, 116, 3, 131, 206, 8)
val m_dwForceAttack by offset(client, 2, 0, READ or SUBTRACT,
		137, 21, 0, 0, 0, 0, 139, 21, 0, 0, 0, 0, 246, 194, 3, 116, 3, 131, 206, 4)
val m_dwViewMatrix by offset(client, 828, 176, READ or SUBTRACT, 129, 198, 0, 0, 0, 0, 136, 69, 146, 15, 182, 192)
val m_dwEntityList by offset(client, 1, 0, READ or SUBTRACT, 187, 0, 0, 0, 0, 131, 255, 1, 15, 140, 0, 0, 0, 0, 59, 248)
val m_dwLocalPlayer by offset(client, 1, 16, READ or SUBTRACT,
		163, 0, 0, 0, 0, 199, 5, 0, 0, 0, 0, 0, 0, 0, 0, 232, 0, 0, 0, 0, 89, 195, 106, 0)

// engine.dll offsets
val m_dwGlobalVars by offset(engine, 18, 0, READ or SUBTRACT, 0x8B, 0x0D, 0x0, 0x0, 0x0, 0x0, 0x83, 0xC4, 0x04,
		0x8B, 0x01, 0x68, 0x0, 0x0, 0x0, 0x0, 0xFF, 0x35)
val m_dwClientState by offset(engine, 1, 0, READ or SUBTRACT,
		161, 0, 0, 0, 0, 243, 15, 17, 128, 0, 0, 0, 0, 217, 70, 4, 217, 5, 0, 0, 0, 0)
val m_dwInGame by offset(engine, 2, 0, READ, 131, 185, 0, 0, 0, 0, 6, 15, 148, 192, 195)
val m_dwMaxPlayer by offset(engine, 7, 0, READ,
		161, 0, 0, 0, 0, 139, 128, 0, 0, 0, 0, 195, 204, 204, 204, 204, 85, 139, 236, 139, 69, 8)
val m_dwMapDirectory by offset(engine, 1, 0, READ, 5, 0, 0, 0, 0, 195, 204, 204, 204, 204, 204, 204, 204, 128, 61)
val m_dwMapname by offset(engine, 1, 0, READ, 5, 0, 0, 0, 0, 195, 204, 204, 204, 204, 204, 204, 204, 161, 0, 0, 0, 0)
val m_dwPlayerInfo by offset(engine, 2, 0, READ, 139, 136, 0, 0, 0, 0, 139, 1, 139, 64, 0, 255, 208, 139, 248)
val m_dwViewAngles by offset(engine, 4, 0, READ, 243, 15, 17, 128, 0, 0, 0, 0, 217, 70, 4, 217, 5, 0, 0, 0, 0)
val m_dwEnginePosition by offset(engine, 4, 0, READ or SUBTRACT, 243, 15, 17, 21, 0, 0, 0, 0, 243, 15, 17, 13,
		0, 0, 0, 0, 243, 15, 17, 5, 0, 0, 0, 0, 243, 15, 17, 61, 0, 0, 0, 0)