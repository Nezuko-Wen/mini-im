package com.wen.open.miniim.common.handler.command;

import com.wen.open.miniim.common.packet.MessagePacket;
import com.wen.open.miniim.common.packet.OnlinePacket;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 文本消息包
 *
 * @author Wen
 * @date 2023/4/13 15:29
 */
@Slf4j
public class MessageHandler extends CommandHandler<MessagePacket> {
    @Override
    void doHandler(ChannelHandlerContext ctx, MessagePacket msg) {
        log.info("{}:{}", ctx.channel().remoteAddress().toString(), msg.getMsg());
        OnlinePacket onlinePacket = new OnlinePacket();
        onlinePacket.setOnline("回复");
        ctx.channel().writeAndFlush(onlinePacket);
    }
}
