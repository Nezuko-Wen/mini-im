package com.wen.open.miniim.common.handler.client;

import com.wen.open.miniim.common.context.GlobalEnvironmentContext;
import com.wen.open.miniim.common.entity.packet.OnlinePacket;
import com.wen.open.miniim.common.util.ParseUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Wen
 * @date 2023/4/13 15:28
 */
public class ConnHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        GlobalEnvironmentContext.liveChannel.add(ParseUtil.tcpIp(ctx));
        GlobalEnvironmentContext.hungChannel.add(ctx.channel());
        OnlinePacket onlinePacket = new OnlinePacket();
        onlinePacket.setOnline(GlobalEnvironmentContext.localhost());
        ctx.channel().writeAndFlush(onlinePacket);
        super.channelActive(ctx);
    }
}
