package com.wen.open.miniim.handler.broadcast;

import com.wen.open.miniim.context.GlobalEnvironmentContext;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author Wen
 * @date 2023/4/7 14:47
 */
@Slf4j
public class DiscoveryClientInHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.executor().parent().execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("我在广播" + i,
                            StandardCharsets.UTF_8), new InetSocketAddress("255.255.255.255", 10000)));
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("-------------cliList");
        GlobalEnvironmentContext.cliMap.keySet().forEach(log::info);
        DatagramPacket packet = (DatagramPacket) msg;
        ByteBuf byteBuf = packet.copy().content();
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        String content = new String(bytes);
        log.debug(packet.sender() + "," + content);
        //在线客户端列表
        GlobalEnvironmentContext.cliMap.putIfAbsent(String.valueOf(packet.sender()), packet);
    }

}
