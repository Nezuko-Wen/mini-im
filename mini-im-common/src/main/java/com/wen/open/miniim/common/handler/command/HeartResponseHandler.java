package com.wen.open.miniim.common.handler.command;

import com.wen.open.miniim.common.entity.packet.HeartPacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务端心跳检测响应
 * @author Wen
 * @date 2023/4/23 09:00
 */
@Slf4j
@ChannelHandler.Sharable
public class HeartResponseHandler extends CommandHandler<HeartPacket>{
    public static final HeartResponseHandler INSTANCE = new HeartResponseHandler();


    @Override
    void doHandler(ChannelHandlerContext ctx, HeartPacket msg) {
        ctx.writeAndFlush(msg);
    }

    protected HeartResponseHandler() {

    }
}
