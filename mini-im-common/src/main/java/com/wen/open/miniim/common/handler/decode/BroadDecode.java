package com.wen.open.miniim.common.handler.decode;

import com.wen.open.miniim.common.context.GlobalEnvironmentContext;
import com.wen.open.miniim.common.packet.BroadcastPacket;
import com.wen.open.miniim.common.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @author Wen
 * @date 2023/4/13 13:44
 */
@Slf4j
public class BroadDecode extends MessageToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object o, List list) {
        log.info("======================online-list========================");
        for (Map.Entry<String, BroadcastPacket> entry : GlobalEnvironmentContext.onlineMap.entrySet()) {
            log.info("ip:{}", entry.getKey());
            log.info("hostname:{}", entry.getValue().getHostname());
            log.info("serverPort:{}", entry.getValue().getServerPort());
        }
        DatagramPacket packet = (DatagramPacket) o;
        ByteBuf byteBuf = packet.copy().content();
        GlobalEnvironmentContext.ip(String.valueOf(packet.sender()).replace("/", "").split(":")[0]);
        list.add(PacketCodeC.INSTANCE.decode(byteBuf));
    }
}
