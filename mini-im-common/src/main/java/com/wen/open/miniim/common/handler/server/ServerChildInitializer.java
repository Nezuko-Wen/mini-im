package com.wen.open.miniim.common.handler.server;

import com.wen.open.miniim.common.handler.PacketCodeCHandler;
import com.wen.open.miniim.common.handler.command.FileHandler;
import com.wen.open.miniim.common.handler.command.MessageHandler;
import com.wen.open.miniim.common.handler.command.OnlineHandler;
import com.wen.open.miniim.common.handler.decode.Spliter;
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
                //客户端下线
                .addLast(CloseHandler.INSTANCE)
                //拆包过滤器
                .addLast(new Spliter())
                .addLast(PacketCodeCHandler.INSTANCE)
                //接受客户端的连接信息
                .addLast(OnlineHandler.INSTANCE)
                .addLast(new FileHandler())
                //文本消息处理
                .addLast(MessageHandler.INSTANCE);
    }
}
