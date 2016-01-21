package org.abendigo.csgo.netvar

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

// DT_CSPlayer
val m_iCrossHairID by cspNetVar("m_bHasDefuser", 0x4C)
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
val m_iWeaponID by netVar("DT_WeaponCSBase", "m_fAccuracyPenalty", 0x2C)