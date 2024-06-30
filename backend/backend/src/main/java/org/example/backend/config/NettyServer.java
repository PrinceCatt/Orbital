package org.example.backend.config;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;


public class NettyServer {
    public void start() {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("received a new connection");
                            ch.pipeline().addLast(new HttpServerCodec());
                            ch.pipeline().addLast(new ChunkedWriteHandler());
                            ch.pipeline().addLast(new HttpObjectAggregator(8192));
                            //ch.pipeline().addLast(new MessageHandler());
                            ch.pipeline().addLast(new WebSocketServerProtocolHandler("/ws",null, true, 65536*10));
                        }
                    });
            System.out.println("netty server start...");

            ChannelFuture future = bootstrap.bind(8080).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
          bossGroup.shutdownGracefully();
          workerGroup.shutdownGracefully();
        }
    }
}
