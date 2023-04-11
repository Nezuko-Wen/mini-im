package com.wen.open.miniim.common.handler;

import com.wen.open.miniim.common.context.GlobalEnvironmentContext;
import com.wen.open.miniim.common.protocol.BroadcastPacket;
import com.wen.open.miniim.common.protocol.Command;
import com.wen.open.miniim.common.protocol.Packet;
import com.wen.open.miniim.common.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;

/**
 * @author Wen
 * @date 2023/4/11 10:38
 */
@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        StringBuilder onlineList = new StringBuilder();
        for (Map.Entry<String, String> entry : GlobalEnvironmentContext.cliMap.entrySet()) {
            onlineList.append("ip:").append(entry.getValue()).append(" hostname:").append(entry.getKey()).append("\n");
        }
        log.info("在线机器:{}", onlineList);
        DatagramPacket packet = (DatagramPacket) msg;
        ByteBuf byteBuf = packet.copy().content();
        Packet data = PacketCodeC.INSTANCE.decode(byteBuf);
        Byte command = data.getCommand();
        if (Objects.equals(Command.BROADCAST.getVal(), command)) {
            BroadcastPacket broadcastPacket = (BroadcastPacket) data;
            //在线客户端列表
            GlobalEnvironmentContext.cliMap.putIfAbsent(broadcastPacket.getHostname(),
                    String.valueOf(packet.sender()).replace("/", "").split(":")[0]);
        }
    }

}
