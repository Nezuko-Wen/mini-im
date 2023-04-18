package com.wen.open.miniim.common.handler.command;

import com.wen.open.miniim.common.context.GlobalEnvironmentContext;
import com.wen.open.miniim.common.packet.BroadcastPacket;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 广播响应
 *
 * @author Wen
 * @date 2023/4/11 10:38
 */
@Slf4j
public class RegisterHandler extends CommandHandler<BroadcastPacket> {

    public static final RegisterHandler INSTANCE = new RegisterHandler();

    @Override
    public void doHandler(ChannelHandlerContext ctx, BroadcastPacket data) {
        GlobalEnvironmentContext.port(data.getServerPort());
        //开启连接
        GlobalEnvironmentContext.register();
    }

    protected RegisterHandler() {

    }
}
