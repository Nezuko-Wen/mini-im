package com.wen.open.miniim.common.handler.broad;

import com.wen.open.miniim.common.context.ConfigContextHolder;
import com.wen.open.miniim.common.context.Constant;
import com.wen.open.miniim.common.context.GlobalEnvironmentContext;
import com.wen.open.miniim.common.packet.BroadcastPacket;
import com.wen.open.miniim.common.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * 广播发现
 * @author Wen
 * @date 2023/4/7 14:47
 */
@Slf4j
public class DiscoveryRequestHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        //开启广播
        holdBroad(ctx);
    }

    private void holdBroad(ChannelHandlerContext ctx) {
        BroadcastPacket broadcastPacket = new BroadcastPacket();
        broadcastPacket.setHostname(GlobalEnvironmentContext.localhost());
        broadcastPacket.setServerPort(GlobalEnvironmentContext.server().port());
        ctx.executor().scheduleAtFixedRate(() -> {
            if (GlobalEnvironmentContext.holdBroad()) {
                ctx.writeAndFlush(new DatagramPacket(encode(broadcastPacket), new InetSocketAddress(Constant.BROADCAST_IP,
                        ConfigContextHolder.config().getBroadPort())));
            }
        }, 0, ConfigContextHolder.config().getBroadDuration(), TimeUnit.SECONDS);
    }

    private ByteBuf encode(BroadcastPacket broadcastPacket) {
       return PacketCodeC.INSTANCE.encode(broadcastPacket);
    }

}
