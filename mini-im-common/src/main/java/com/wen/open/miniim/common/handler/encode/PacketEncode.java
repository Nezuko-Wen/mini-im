package com.wen.open.miniim.common.handler.encode;

import com.wen.open.miniim.common.packet.Packet;
import com.wen.open.miniim.common.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author Wen
 * @date 2023/4/13 15:19
 */
public class PacketEncode extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) {
        PacketCodeC.INSTANCE.encode(packet, byteBuf);
    }
}
