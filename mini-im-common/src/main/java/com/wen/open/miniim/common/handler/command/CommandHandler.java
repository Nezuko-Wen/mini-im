package com.wen.open.miniim.common.handler.command;

import com.wen.open.miniim.common.packet.MessagePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Wen
 * @date 2023/4/13 13:51
 */
public abstract class CommandHandler<T> extends SimpleChannelInboundHandler<T> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, T msg) {
        doHandler(ctx, msg);
        MessagePacket messagePacket1 = new MessagePacket();
        messagePacket1.setMsg("其中，第一个参数指的是数据包的最大长度，第二个参数指的是长度域的偏移量，第三个参数指的是长度域的 长度，这样一个拆包器写好之后，只需要在 pipeline 的最前面加上这个拆包器。");
        ctx.channel().writeAndFlush(messagePacket1);
    }

    abstract void doHandler(ChannelHandlerContext ctx, T msg);
}
