package org.abendigo.controller

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import org.abendigo.DEBUG
import org.abendigo.plugin.Plugins

internal class Handler : ByteToMessageDecoder() {

	override fun decode(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>) {
		val opcode = buf.readUnsignedByte().toInt()
		when (opcode) {
			0 -> {
				val response = ctx.alloc().buffer()
				response.writeByte(0)
				response.writeByte(Plugins.map.size)
				for ((name, plugin) in Plugins.map) {
					response.writeString(name)
					response.writeBoolean(plugin.enabled)
				}
				ctx.writeAndFlush(response)
			}
			1 -> Plugins.disable(buf.readString())
			2 -> Plugins.enable(buf.readString())
			3 -> System.exit(0)
			else -> println("Unhandled opcode ($opcode)")
		}
	}

	override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
		if (DEBUG) cause.printStackTrace()
	}

	private val sb = StringBuilder()

	private fun ByteBuf.readString(): String {
		val length = readUnsignedByte().toInt()
		sb.delete(0, sb.length)
		for (i in 1..length) sb.append(readUnsignedByte().toChar())
		return sb.toString()
	}

	private fun ByteBuf.writeString(string: String) {
		val bytes = string.toByteArray()
		writeByte(bytes.size)
		writeBytes(bytes)
	}

}