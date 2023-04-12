package com.wen.open.miniim.common.handler;

import com.wen.open.miniim.common.context.Constant;
import com.wen.open.miniim.common.context.GlobalEnvironmentContext;
import com.wen.open.miniim.common.protocol.BroadcastPacket;
import com.wen.open.miniim.common.protocol.PacketCodeC;
import com.wen.open.miniim.common.util.ConfigContextHolder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * @author Wen
 * @date 2023/4/7 14:47
 */
@Slf4j
public class DiscoveryClientInHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        //开启广播
        holdBroad(ctx);
    }

    private void holdBroad(ChannelHandlerContext ctx) {
        String hostname = ConfigContextHolder.config().getDefaultHostname();
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            hostname = localhost.getHostName();
        } catch (UnknownHostException e) {
            log.warn("获取主机名失败", e);
        }
        BroadcastPacket broadcastPacket = new BroadcastPacket();
        broadcastPacket.setHostname(hostname);
        broadcastPacket.setServerPort(GlobalEnvironmentContext.server().port());
        ctx.executor().scheduleAtFixedRate(() -> {
            ctx.writeAndFlush(new DatagramPacket(encode(broadcastPacket), new InetSocketAddress(Constant.BROADCAST_IP,
                    ConfigContextHolder.config().getBroadPort())));
        }, 0, ConfigContextHolder.config().getBroadDuration(), TimeUnit.SECONDS);
    }

    private ByteBuf encode(BroadcastPacket broadcastPacket) {
       return PacketCodeC.INSTANCE.encode(broadcastPacket);
    }

}
