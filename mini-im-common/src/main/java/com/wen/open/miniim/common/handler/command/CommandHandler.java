package com.wen.open.miniim.common.handler.command;

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
    }

    abstract void doHandler(ChannelHandlerContext ctx, T msg);
}
