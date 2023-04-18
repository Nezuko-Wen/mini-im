package com.wen.open.miniim.common.handler.command;

import com.wen.open.miniim.common.context.GlobalEnvironmentContext;
import com.wen.open.miniim.common.util.ParseUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Wen
 * @date 2023/4/17 18:30
 */
@ChannelHandler.Sharable
public class CloseHandler extends ChannelInboundHandlerAdapter {
    public static final CloseHandler INSTANCE = new CloseHandler();

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        String clientIp = ParseUtil.tcpIp(ctx);
        GlobalEnvironmentContext.onlineMap.remove(clientIp);
        GlobalEnvironmentContext.liveChannel.remove(clientIp);
    }

    protected CloseHandler() {

    }
}
