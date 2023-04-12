package com.wen.open.miniim.common.handler;

import com.wen.open.miniim.common.context.GlobalEnvironmentContext;
import com.wen.open.miniim.common.protocol.BroadcastPacket;
import com.wen.open.miniim.common.protocol.Command;
import com.wen.open.miniim.common.protocol.Packet;
import com.wen.open.miniim.common.protocol.PacketCodeC;
import com.wen.open.miniim.common.util.LogTestUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author Wen
 * @date 2023/4/11 10:38
 */
@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        log.info("======================online-list========================");
//        for (Map.Entry<String, BroadcastPacket> entry : GlobalEnvironmentContext.onlineMap.entrySet()) {
//            log.info("ip:{}", entry.getKey());
//            log.info("hostname:{}", entry.getValue().getHostname());
//            log.info("serverPort:{}", entry.getValue().getServerPort());
//        }
        GlobalEnvironmentContext.hungChannel.add(ctx.channel());
        DatagramPacket packet = (DatagramPacket) msg;
        ByteBuf byteBuf = packet.copy().content();
        Packet data = PacketCodeC.INSTANCE.decode(byteBuf);
        BroadcastPacket broadPacket = (BroadcastPacket) data;
        Byte command = broadPacket.getCommand();
        String ip = String.valueOf(packet.sender()).replace("/", "").split(":")[0];
        if (Objects.equals(Command.BROADCAST.getVal(), command)) {
            //在线客户端列表
            GlobalEnvironmentContext.onlineMap.putIfAbsent(ip, broadPacket);
        }

        //开启连接
        GlobalEnvironmentContext.currentIp.set(ip);
        GlobalEnvironmentContext.client().connect(broadPacket.getServerPort(), ip);
        GlobalEnvironmentContext.currentIp.remove();

        while (true) {
            if (GlobalEnvironmentContext.liveChannel.containsKey(ip)) {
                GlobalEnvironmentContext.liveChannel.get(ip)
                        .writeAndFlush(LogTestUtil.write("mac" + ip + " 我收到你的信息了!"));
                break;
            }
        }
    }
}
