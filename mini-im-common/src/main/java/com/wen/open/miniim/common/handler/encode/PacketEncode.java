package com.wen.open.miniim.common.handler.encode;

import com.wen.open.miniim.common.packet.Packet;
import com.wen.open.miniim.common.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Wen
 * @date 2023/4/13 15:19
 */
@Slf4j
public class PacketEncode extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) {
        PacketCodeC.INSTANCE.encode(packet, byteBuf);
    }
}
