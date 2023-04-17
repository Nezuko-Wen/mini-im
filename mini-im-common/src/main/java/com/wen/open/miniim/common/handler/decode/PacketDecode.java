package com.wen.open.miniim.common.handler.decode;

import com.wen.open.miniim.common.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author Wen
 * @date 2023/4/13 15:53
 */
public class PacketDecode extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
        list.add(PacketCodeC.INSTANCE.decode(byteBuf));
    }
}
