package com.wen.open.miniim.common.handler.client;

import com.wen.open.miniim.common.context.GlobalEnvironmentContext;
import com.wen.open.miniim.common.packet.OnlinePacket;
import com.wen.open.miniim.common.protocol.PacketCodeC;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;

/**
 * @author Wen
 * @date 2023/4/13 15:28
 */
public class ConnHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        String ip = (String) ctx.attr(AttributeKey.valueOf("ip")).get();
        OnlinePacket packet = new OnlinePacket();
        packet.setOnline(GlobalEnvironmentContext.localhost() + "- 上线了");
        GlobalEnvironmentContext.liveChannel.putIfAbsent(ip, ctx.channel());
        ctx.writeAndFlush(PacketCodeC.INSTANCE.encode(packet));
    }

}
