package com.wen.open.miniim.common.handler.command;

import com.wen.open.miniim.common.packet.MessagePacket;
import com.wen.open.miniim.common.packet.OnlinePacket;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Wen
 * @date 2023/4/13 15:44
 */
@Slf4j
public class OnlineHandler extends CommandHandler<OnlinePacket>{
    @Override
    void doHandler(ChannelHandlerContext ctx, OnlinePacket msg) {
        log.info(msg.getOnline());
        MessagePacket messagePacket = new MessagePacket();
        messagePacket.setMsg("欢迎你");
        ctx.channel().writeAndFlush(messagePacket);
    }
}
