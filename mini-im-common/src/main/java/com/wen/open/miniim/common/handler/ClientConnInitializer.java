package com.wen.open.miniim.common.handler;

import com.wen.open.miniim.common.context.GlobalEnvironmentContext;
import com.wen.open.miniim.common.util.LogTestUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;


/**
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
        channel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
            @Override
            public void channelActive(ChannelHandlerContext ctx) {
                String ip = (String) ctx.attr(AttributeKey.valueOf("ip")).get();
                ctx.writeAndFlush(LogTestUtil.write("你好,zzw"));
                log.info("conn ip:{}", ip);
                GlobalEnvironmentContext.liveChannel.putIfAbsent(ip, ctx.channel());
            }
        });

    }
}
