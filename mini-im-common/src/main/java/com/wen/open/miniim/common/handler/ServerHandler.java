package com.wen.open.miniim.common.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author Wen
 * @date 2023/4/12 18:21
 */
@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        InetSocketAddress clientAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = clientAddress.getAddress().getHostAddress();
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(new Date() + ": 服务端读到数据 -> " + byteBuf.toString(StandardCharsets.UTF_8));
    }
}
