package com.wen.open.miniim.common.handler.command;

import com.wen.open.miniim.common.context.GlobalEnvironmentContext;
import com.wen.open.miniim.common.packet.BroadcastPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 广播响应
 *
 * @author Wen
 * @date 2023/4/11 10:38
 */
@Slf4j
public class RegisterHandler extends SimpleChannelInboundHandler<BroadcastPacket> {
//    @Override
//    public void doHandler(ChannelHandlerContext ctx, BroadcastPacket data) {
//        GlobalEnvironmentContext.port(data.getServerPort());
//        //开启连接
//        GlobalEnvironmentContext.register(data);
//    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BroadcastPacket msg) throws Exception {
        GlobalEnvironmentContext.port(msg.getServerPort());
        //开启连接
        GlobalEnvironmentContext.register(msg);
//        MessagePacket messagePacket1 = new MessagePacket();
//        messagePacket1.setMsg("其中，第一个参数指的是数据包的最大长度，第二个参数指的是长度域的偏移量，第三个参数指的是长度域的 长度，这样一个拆包器写好之后，只需要在 pipeline 的最前面加上这个拆包器。");
//        ctx.channel().writeAndFlush(messagePacket1);
    }
}
