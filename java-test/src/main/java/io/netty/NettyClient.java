package io.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
    public static void main(String[] args) throws Exception {
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        EventLoopGroup workerGroup = new NioEventLoopGroup(); // (1)

        try {
            Bootstrap b = new Bootstrap(); // (2)
            b.group(workerGroup); // (3)
            b.channel(NioSocketChannel.class); // (4)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (5)
            b.handler(new ChannelInitializer<SocketChannel>() { // (6)
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    // TODO: 2021/4/15 加入处理器
                }
            });

            // Start the client.
            ChannelFuture f = b.connect(host, port).sync(); // (7)

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
