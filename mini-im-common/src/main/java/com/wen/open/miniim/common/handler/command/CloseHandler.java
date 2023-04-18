package com.wen.open.miniim.common.handler.command;

import com.wen.open.miniim.common.context.GlobalEnvironmentContext;
import com.wen.open.miniim.common.util.ParseUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Wen
 * @date 2023/4/17 18:30
 */
public class CloseHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        GlobalEnvironmentContext.onlineMap.remove(ParseUtil.ip(ctx.channel().remoteAddress()));
    }
}
