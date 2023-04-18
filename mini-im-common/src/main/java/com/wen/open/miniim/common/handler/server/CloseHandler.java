package com.wen.open.miniim.common.handler.server;

import com.wen.open.miniim.common.context.GlobalEnvironmentContext;
import com.wen.open.miniim.common.util.ParseUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Wen
 * @date 2023/4/17 18:30
 */
@ChannelHandler.Sharable
@Slf4j
public class CloseHandler extends ChannelInboundHandlerAdapter {
    public static final CloseHandler INSTANCE = new CloseHandler();

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        String clientIp = ParseUtil.tcpIp(ctx);
        GlobalEnvironmentContext.onlineMap.remove(clientIp);
        GlobalEnvironmentContext.liveChannel.remove(clientIp);
        log.info(clientIp + " 下线了");
    }

    protected CloseHandler() {

    }
}
