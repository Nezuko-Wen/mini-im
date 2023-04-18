package com.wen.open.miniim.common.handler.client;

import com.wen.open.miniim.common.handler.PacketCodeCHandler;
import com.wen.open.miniim.common.handler.command.FileHandler;
import com.wen.open.miniim.common.handler.command.MessageHandler;
import com.wen.open.miniim.common.handler.decode.Spliter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;


/**
 * 连接客户端
 * @author Wen
 * @date 2023/4/12 16:09
 */
@Slf4j
public class ClientConnInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) {
        channel.pipeline()
                .addLast(new ConnHandler())
                .addLast(new Spliter())
                .addLast(PacketCodeCHandler.INSTANCE)
                .addLast(MessageHandler.INSTANCE)
                .addLast(new FileHandler());
    }
}
