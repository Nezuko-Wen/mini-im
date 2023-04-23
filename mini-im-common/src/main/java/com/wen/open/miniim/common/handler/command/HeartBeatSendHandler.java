package com.wen.open.miniim.common.handler.command;

import com.wen.open.miniim.common.entity.packet.HeartPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 客户端心跳请求
 *
 * @author Wen
 * @date 2023/4/23 08:59
 */
@Slf4j
public class HeartBeatSendHandler extends ChannelInboundHandlerAdapter {
    private static final int HEARTBEAT_INTERVAL = 5;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        asyncSendHeart(ctx);
        super.channelActive(ctx);
    }

    private void asyncSendHeart(ChannelHandlerContext ctx) {
        ctx.executor().scheduleWithFixedDelay(() -> {
            if (ctx.channel().isActive()) {
                ctx.writeAndFlush(new HeartPacket());
            }
        }, HEARTBEAT_INTERVAL, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
    }
}
