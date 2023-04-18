package com.wen.open.miniim.common.handler.client;

import com.wen.open.miniim.common.context.GlobalEnvironmentContext;
import com.wen.open.miniim.common.packet.MessagePacket;
import com.wen.open.miniim.common.packet.OnlinePacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;

import java.util.Scanner;

/**
 * @author Wen
 * @date 2023/4/13 15:28
 */
public class ConnHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        String ip = (String) ctx.attr(AttributeKey.valueOf("ip")).get();
        GlobalEnvironmentContext.liveChannel.putIfAbsent(ip, ctx.channel());
//        startConsoleThread(ctx.channel());
        GlobalEnvironmentContext.hungChannel.add(ctx.channel());
        OnlinePacket onlinePacket = new OnlinePacket();
        onlinePacket.setOnline(GlobalEnvironmentContext.localhost());
        ctx.channel().writeAndFlush(onlinePacket);
    }
    private void startConsoleThread(Channel channel) {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                System.out.println("输入消息发送至服务端: ");
                Scanner sc = new Scanner(System.in);
                String line = sc.nextLine();
                MessagePacket messagePacket = new MessagePacket();
                messagePacket.setMsg(line);
                channel.writeAndFlush(messagePacket);
            }
        }).start();
    }
}
