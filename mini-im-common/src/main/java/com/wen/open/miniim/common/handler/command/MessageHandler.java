package com.wen.open.miniim.common.handler.command;

import com.wen.open.miniim.common.packet.MessagePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 文本消息包
 * @author Wen
 * @date 2023/4/13 15:29
 */
@Slf4j
public class MessageHandler extends SimpleChannelInboundHandler<MessagePacket> {
//    @Override
//    void doHandler(ChannelHandlerContext ctx, MessagePacket msg) {
//        log.info(msg.getMsg());
//        MessagePacket messagePacket1 = new MessagePacket();
//        messagePacket1.setMsg("其中，第一个参数指的是数据包的最大长度，第二个参数指的是长度域的偏移量，第三个参数指的是长度域的 长度，这样一个拆包器写好之后，只需要在 pipeline 的最前面加上这个拆包器。");
//        ctx.channel().writeAndFlush(messagePacket1);
//    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessagePacket msg) throws Exception {
        log.info(ctx.channel().remoteAddress().toString());
        log.info(msg.getMsg());
        MessagePacket messagePacket1 = new MessagePacket();
        messagePacket1.setMsg("其中，第一个参数指的是数据包的最大长度，第二个参数指的是长度域的偏移量，第三个参数指的是长度域的 长度，这样一个拆包器写好之后，只需要在 pipeline 的最前面加上这个拆包器。");
        ctx.channel().writeAndFlush(messagePacket1);
    }
}
