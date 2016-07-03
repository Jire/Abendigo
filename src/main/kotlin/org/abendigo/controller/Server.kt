package org.abendigo.controller

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.oio.OioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.channel.socket.oio.OioServerSocketChannel

object Server {

	const val DEFAULT_PORT = 58585

	fun bind(port: Int = DEFAULT_PORT) = ServerBootstrap()
			.group(NioEventLoopGroup())
			.channel(NioServerSocketChannel::class.java)
			.childHandler(@Sharable object : ChannelInitializer<SocketChannel>() {
				override fun initChannel(ch: SocketChannel) {
					ch.pipeline().addLast(Handler())
				}
			})
			.bind(port)

}