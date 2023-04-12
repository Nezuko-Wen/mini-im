package com.wen.open.miniim.common.handler;

import com.wen.open.miniim.common.context.GlobalEnvironmentContext;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author Wen
 * @date 2023/4/12 18:21
 */
@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        InetSocketAddress clientAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = clientAddress.getAddress().getHostAddress();
        ByteBuf byteBuf = (ByteBuf) msg;
        log.info(new Date() + ": 服务端读到数据 -> " + byteBuf.toString(StandardCharsets.UTF_8));
    }
}
