package com.wen.open.miniim.common.handler.command;

import com.wen.open.miniim.common.packet.MessagePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 文本消息包
 *
 * @author Wen
 * @date 2023/4/13 15:29
 */
@ChannelHandler.Sharable
@Slf4j
public class MessageHandler extends CommandHandler<MessagePacket> {
    public static final MessageHandler INSTANCE = new MessageHandler();

    @Override
    void doHandler(ChannelHandlerContext ctx, MessagePacket msg) {
        log.info("{}:{}", ctx.channel().remoteAddress().toString(), msg.getMsg());
    }
    protected MessageHandler() {

    }
}
