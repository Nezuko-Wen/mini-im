package com.wen.open.miniim.common.handler.command;

import com.wen.open.miniim.common.context.GlobalEnvironmentContext;
import com.wen.open.miniim.common.packentity.ClientInfo;
import com.wen.open.miniim.common.packet.OnlinePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Wen
 * @date 2023/4/13 15:44
 */
@Slf4j
public class OnlineHandler extends CommandHandler<OnlinePacket> {
    @Override
    void doHandler(ChannelHandlerContext ctx, OnlinePacket msg) {
        log.info(msg.getOnline());
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setHostname(msg.getOnline());
        clientInfo.setChannel((NioSocketChannel) ctx.channel());
        GlobalEnvironmentContext.onlineMap.putIfAbsent(ctx.channel().remoteAddress().toString(), clientInfo);
    }
}
