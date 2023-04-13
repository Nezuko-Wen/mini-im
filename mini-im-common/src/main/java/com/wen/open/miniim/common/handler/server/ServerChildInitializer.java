package com.wen.open.miniim.common.handler.server;

import com.wen.open.miniim.common.handler.command.OnlineHandler;
import com.wen.open.miniim.common.handler.decode.PacketDecode;
import com.wen.open.miniim.common.handler.encode.PacketEncode;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 服务器
 * @author Wen
 * @date 2023/4/13 15:47
 */
public class ServerChildInitializer extends ChannelInitializer<NioSocketChannel> {
    //服务器读写数据过程中的逻辑
    @Override
    protected void initChannel(NioSocketChannel channel) {
        channel.pipeline()
                .addLast(new PacketDecode())
                //接受客户端的连接信息
                .addLast(new OnlineHandler())
                .addLast(new PacketEncode());
    }
}
