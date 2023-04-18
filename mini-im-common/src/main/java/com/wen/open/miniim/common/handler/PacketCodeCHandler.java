package com.wen.open.miniim.common.handler;

import com.wen.open.miniim.common.entity.packet.Packet;
import com.wen.open.miniim.common.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

/**
 * @author Wen
 * @date 2023/4/18 10:10
 */
@ChannelHandler.Sharable
public class PacketCodeCHandler extends MessageToMessageCodec<ByteBuf, Packet> {
    public static final PacketCodeCHandler INSTANCE = new PacketCodeCHandler();

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, List<Object> list) {
        ByteBuf byteBuf = ctx.channel().alloc().ioBuffer();
        PacketCodeC.INSTANCE.encode(packet, byteBuf);
        list.add(byteBuf);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) {
        Packet decode = PacketCodeC.INSTANCE.decode(byteBuf);
        list.add(decode);
    }

    protected PacketCodeCHandler() {

    }
}
