package com.wen.open.miniim.common.handler.command;

import com.wen.open.miniim.common.context.GlobalEnvironmentContext;
import com.wen.open.miniim.common.packet.ClosePacket;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author Wen
 * @date 2023/4/17 18:30
 */
public class CloseHandler extends CommandHandler<ClosePacket>{
    @Override
    void doHandler(ChannelHandlerContext ctx, ClosePacket msg) {
        GlobalEnvironmentContext.liveChannel.remove(msg.getIp());
        GlobalEnvironmentContext.onlineMap.remove(msg.getIp());
    }
}
