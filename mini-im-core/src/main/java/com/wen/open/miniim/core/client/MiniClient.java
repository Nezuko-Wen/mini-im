package com.wen.open.miniim.core.client;

import com.wen.open.miniim.common.handler.broadcast.DiscoveryClientInHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.springframework.stereotype.Component;

/**
 * @author Wen
 * @date 2023/4/7 14:30
 */
@Component
public class MiniClient {
    public void startUdp() throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new DiscoveryClientInHandler());
            b.bind(10000).sync().channel().closeFuture().await();
        } finally {
            group.shutdownGracefully();
        }
    }
}
