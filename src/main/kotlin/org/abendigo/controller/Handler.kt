package org.abendigo.controller

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import org.abendigo.plugin.Plugins

internal class Handler : ByteToMessageDecoder() {

	override fun decode(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>) {
		val opcode = buf.readUnsignedByte().toInt()
		when (opcode) {
			0 -> {
				val pluginName = buf.readString()
				Plugins.enable(pluginName)
			}
			1 -> {
				val pluginName = buf.readString()
				Plugins.disable(pluginName)
			}
			2 -> {
				val pluginName = buf.readString()
				val plugin = Plugins.byName(pluginName)

				val response = ctx.alloc().buffer(1)
				response.writeBoolean(if (plugin == null) false else plugin.enabled)
				out.add(response)
			}
			3 -> {
				System.exit(0)
			}
			else -> println("Unhandled opcode ($opcode)")
		}
	}

	private val sb = StringBuilder()

	private fun ByteBuf.readString(): String {
		val length = readUnsignedByte().toInt()
		sb.delete(0, sb.length)
		for (i in 1..length) sb.append(readUnsignedByte().toChar())
		return sb.toString()
	}

}