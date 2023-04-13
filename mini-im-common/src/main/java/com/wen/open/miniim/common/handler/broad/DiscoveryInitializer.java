package com.wen.open.miniim.common.handler.broad;

import com.wen.open.miniim.common.handler.command.RegisterHandler;
import com.wen.open.miniim.common.handler.decode.BroadDecode;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * @author Wen
 * @date 2023/4/13 10:15
 */
public class DiscoveryInitializer extends ChannelInitializer<NioDatagramChannel> {
    @Override
    protected void initChannel(NioDatagramChannel channel) {
        channel.pipeline()
                .addLast(new DiscoveryRequestHandler())
                .addLast(new BroadDecode())
                .addLast(new RegisterHandler());
    }
}
