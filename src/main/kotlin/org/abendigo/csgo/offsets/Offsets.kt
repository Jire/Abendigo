@file:JvmName("Offsets")

package org.abendigo.csgo.offsets

import org.abendigo.csgo.Client.clientDLL
import org.abendigo.csgo.csgoModule
import org.abendigo.csgo.engineDLL

val worldDecal by scanOffset(clientDLL, 0, 0, 0, "DT_TEWorldDecal")
val firstClass by scanOffset(clientDLL, 0x2B, 0, READ, worldDecal)

val gameDirectoryPointer by scanOffset(csgoModule, 1, 0, READ or SUBTRACT, 0xB9, 0, 0, 0, 0, 0x8D, 0x51)

// client.dll offsets
const val m_bDormant = 0xE9
const val m_dwIndex = 0x64
const val m_dwModel = 0x6C
val m_dwRadarBase by scanOffset(clientDLL, 1, 0, READ or SUBTRACT, 161, 0, 0, 0, 0, 139, 12, 176, 139, 1, 255, 80, 0,
		70, 59, 53, 0, 0, 0, 0, 124, 234, 139, 13, 0, 0, 0, 0)
val m_dwWeaponTable by scanOffset(clientDLL, 1, 0, READ or SUBTRACT,
		161, 0, 0, 0, 0, 15, 183, 201, 3, 201, 139, 68, 0, 12, 195)
val m_dwWeaponTableIndex by scanOffset(clientDLL, 3, 0, READ, 102, 139, 142, 0, 0, 0, 0, 232, 0, 0, 0, 0, 5, 0, 0, 0, 0, 80)
val m_dwInput by scanOffset(clientDLL, 1, 0, READ or SUBTRACT, 0xB9, 0, 0, 0, 0, 0xFF, 0x75, 0x08, 0xE8, 0, 0, 0, 0, 0x8B, 0x06)
val m_dwGlowObject by scanOffset(clientDLL, 1, 4, READ or SUBTRACT, 0xA1, 0, 0, 0, 0, 0xA8, 0x01, 0x75, 0x4E, 0x0F, 0x57, 0xC0)
val m_dwForceJump by scanOffset(clientDLL, 2, 0, READ or SUBTRACT,
		137, 21, 0, 0, 0, 0, 139, 21, 0, 0, 0, 0, 246, 194, 3, 116, 3, 131, 206, 8)
val m_dwForceAttack by scanOffset(clientDLL, 2, 0, READ or SUBTRACT,
		137, 21, 0, 0, 0, 0, 139, 21, 0, 0, 0, 0, 246, 194, 3, 116, 3, 131, 206, 4)
val m_dwViewMatrix by scanOffset(clientDLL, 828, 176, READ or SUBTRACT, 129, 198, 0, 0, 0, 0, 136, 69, 154, 15, 182, 192)
val m_dwEntityList by scanOffset(clientDLL, 1, 0, READ or SUBTRACT, 187, 0, 0, 0, 0, 131, 255, 1, 15, 140, 0, 0, 0, 0, 59, 248)
val m_dwLocalPlayer by scanOffset(clientDLL, 1, 16, READ or SUBTRACT,
		163, 0, 0, 0, 0, 199, 5, 0, 0, 0, 0, 0, 0, 0, 0, 232, 0, 0, 0, 0, 89, 195, 106, 0)

// engine.dll offsets
val m_dwGlobalVars by scanOffset(engineDLL, 18, 0, READ or SUBTRACT, 0x8B, 0x0D, 0x0, 0x0, 0x0, 0x0, 0x83, 0xC4, 0x04,
		0x8B, 0x01, 0x68, 0x0, 0x0, 0x0, 0x0, 0xFF, 0x35)
val m_dwClientState by scanOffset(engineDLL, 1, 0, READ or SUBTRACT,
		161, 0, 0, 0, 0, 243, 15, 17, 128, 0, 0, 0, 0, 217, 70, 4, 217, 5, 0, 0, 0, 0)
val m_dwInGame by scanOffset(engineDLL, 2, 0, READ, 131, 185, 0, 0, 0, 0, 6, 15, 148, 192, 195)
val m_dwMaxPlayer by scanOffset(engineDLL, 7, 0, READ,
		161, 0, 0, 0, 0, 139, 128, 0, 0, 0, 0, 195, 204, 204, 204, 204, 85, 139, 236, 139, 69, 8)
val m_dwMapDirectory by scanOffset(engineDLL, 1, 0, READ, 5, 0, 0, 0, 0, 195, 204, 204, 204, 204, 204, 204, 204, 128, 61)
val m_dwMapname by scanOffset(engineDLL, 1, 0, READ, 5, 0, 0, 0, 0, 195, 204, 204, 204, 204, 204, 204, 204, 161, 0, 0, 0, 0)
val m_dwPlayerInfo by scanOffset(engineDLL, 2, 0, READ, 139, 136, 0, 0, 0, 0, 139, 1, 139, 64, 0, 255, 208, 139, 248)
val m_dwViewAngles by scanOffset(engineDLL, 4, 0, READ, 0xF3, 0x0F, 0x11, 0x80, 0, 0, 0, 0, 0xD9, 0x46, 0x04, 0xD9, 0x05, 0, 0, 0, 0)
val m_dwEnginePosition by scanOffset(engineDLL, 4, 0, READ or SUBTRACT, 243, 15, 17, 21, 0, 0, 0, 0, 243, 15, 17, 13,
		0, 0, 0, 0, 243, 15, 17, 5, 0, 0, 0, 0, 243, 15, 17, 61, 0, 0, 0, 0)
val m_bSendPacket by scanOffset(engineDLL, 0, 0, SUBTRACT, 0x01, 0x8B, 0x01, 0x8B, 0x40, 0x10) // thanks WasserEsser!
val m_dwForceFullUpdate by scanOffset(engineDLL, 0x3, 0, READ or SUBTRACT, 0xB0, 0xFF, 0xB7, 0x00, 0x00, 0x00, 0x00, 0xE8)

// DT_BaseEntity
val m_bSpotted by beNetVar()
val m_vecOrigin by beNetVar()
val m_iTeamNum by beNetVar()

// DT_BasePlayer
val m_fFlags by bpNetVar()
val m_iHealth by bpNetVar()
val m_vecViewOffset by bpNetVar(index = 0)
val m_vecVelocity by bpNetVar(index = 0)
val m_vecPunch by bpNetVar("m_aimPunchAngle")
val nActiveWeapon by bpNetVar()
val nTickBase by bpNetVar()
val m_lifeState by bpNetVar()
val m_hActiveWeapon by bpNetVar()
val m_nTickBase by bpNetVar()
val m_nButtons by bpNetVar()
val m_hMyWeapons by bpNetVar()

// DT_CSPlayer
val m_iCrossHairID by cspNetVar("m_bHasDefuser", 0x5C)
val m_bIsScoped by cspNetVar()
val m_iShotsFired by cspNetVar()
val m_flFlashMaxAlpha by cspNetVar()

// DT_BaseAnimating
val m_dwBoneMatrix by netVar("DT_BaseAnimating", "m_nForceBone", 0x1C)

// DT_BaseCombatWeapon
val m_flNextPrimaryAttack by bcwNetVar()
val m_iClip1 by bcwNetVar()
val m_iClip2 by bcwNetVar()

// DT_WeaponCSBase
val m_iWeaponID by netVar("DT_WeaponCSBase", "m_fAccuracyPenalty", 0x30) // thanks n1BogOLlyQ
val m_iItemDefinitionIndex by wepNetVar()
val m_iAccountID by wepNetVar()
val m_OriginalOwnerXuidLow by wepNetVar()
val m_OriginalOwnerXuidHigh by wepNetVar()
val m_iItemIDLow by wepNetVar()
val m_iItemIDHigh by wepNetVar()
val m_nFallbackPaintKit by wepNetVar()
val m_nFallbackSeed by wepNetVar()
val m_flFallbackWear by wepNetVar()
val m_nFallbackStatTrak by wepNetVar()
val m_iEntityQuality by wepNetVar()
val m_szCustomName by wepNetVar()