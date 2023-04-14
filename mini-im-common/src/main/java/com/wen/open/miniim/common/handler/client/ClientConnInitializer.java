package com.wen.open.miniim.common.handler.client;

import com.wen.open.miniim.common.handler.command.MessageHandler;
import com.wen.open.miniim.common.handler.decode.PacketDecode;
import com.wen.open.miniim.common.handler.encode.PacketEncode;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;


/**
 * 连接客户端
 * @author Wen
 * @date 2023/4/12 16:09
 */
@Slf4j
public class ClientConnInitializer extends ChannelInitializer<SocketChannel> {

    private final String ip;

    public ClientConnInitializer(String ip) {
        this.ip = ip;
    }

    @Override
    protected void initChannel(SocketChannel channel) {
        ChannelHandlerContext context = channel.pipeline().firstContext();
        context.attr(AttributeKey.valueOf("ip")).set(this.ip);
        channel.pipeline()
                .addLast(new ConnHandler())
                .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4))
                .addLast(new PacketDecode())
                .addLast(new MessageHandler())
                .addLast(new PacketEncode());
    }
}
