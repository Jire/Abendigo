package org.abendigo.util

import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinUser

private const val MOUSEEVENTF_MOVE = 0x0001L
private const val MOUSEEVENTF_ABSOLUTE = 0x8000L

private val DWORD_ONE = WinDef.DWORD(1)

@Suppress("UNCHECKED_CAST")
private val input = object : WinUser.INPUT() {

	init {
		type = WinDef.DWORD(WinUser.INPUT.INPUT_MOUSE.toLong())

		input.mi.dx = WinDef.LONG(0)
		input.mi.dy = WinDef.LONG(0)
		input.mi.mouseData = WinDef.DWORD(0)
		input.mi.dwFlags = WinDef.DWORD(MOUSEEVENTF_MOVE)
		input.setType("mi")

	}
}.toArray(1) as Array<WinUser.INPUT>

private val inputSize by lazy(LazyThreadSafetyMode.NONE) { input[0].size() }

fun mouseMove(dx: Int, dy: Int) {
	input[0].input.mi.dx.setValue(dx.toLong())
	input[0].input.mi.dy.setValue(dy.toLong())

	User32.INSTANCE.SendInput(DWORD_ONE, input, inputSize)
}