package com.wen.open.miniim.common.handler.command;

import com.wen.open.miniim.common.entity.packet.HeartPacket;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Wen
 * @date 2023/4/23 09:33
 */
@Slf4j
public class HeartHandler extends CommandHandler<HeartPacket>{
    @Override
    void doHandler(ChannelHandlerContext ctx, HeartPacket msg) {
        log.info("收到心跳响应");
    }
}
