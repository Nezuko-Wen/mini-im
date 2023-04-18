package com.wen.open.miniim.common.handler.command;

import com.wen.open.miniim.common.context.GlobalEnvironmentContext;
import com.wen.open.miniim.common.packet.ClosePacket;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Wen
 * @date 2023/4/17 18:30
 */
@Slf4j
public class CloseHandler extends CommandHandler<ClosePacket>{
    @Override
    void doHandler(ChannelHandlerContext ctx, ClosePacket msg) {
        log.info(msg.getMsg());
        GlobalEnvironmentContext.onlineMap.remove(ctx.channel().remoteAddress().toString());
    }
}
