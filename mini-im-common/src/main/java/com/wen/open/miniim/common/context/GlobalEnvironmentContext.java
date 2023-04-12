package com.wen.open.miniim.common.context;

import com.wen.open.miniim.common.handler.ClientConnInitializer;
import com.wen.open.miniim.common.packentity.ClientBoot;
import com.wen.open.miniim.common.packentity.ServerBoot;
import com.wen.open.miniim.common.protocol.BroadcastPacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Wen
 * @date 2023/4/7 15:04
 */
@Slf4j
public class GlobalEnvironmentContext {

    /** 客户端的地址列表 */
    public static final ConcurrentHashMap<String, BroadcastPacket> onlineMap = new ConcurrentHashMap<>();

    public static final ConcurrentHashMap<String, Channel> liveChannel = new ConcurrentHashMap<>();

    public static ThreadLocal<String> currentIp = new ThreadLocal<>();

    public static ServerBoot serverBoot;

    public static ClientBoot clientBoot;

    /* 扫描客户端线程 */
    public static boolean scanExecutor = false;

    public static void serverLive(ServerBoot boot) {
        GlobalEnvironmentContext.serverBoot = boot;
    }

    public static ServerBoot server() {
        return serverBoot;
    }

    public static void clientLive(ClientBoot boot) {
        GlobalEnvironmentContext.clientBoot = boot;
    }

    public static ClientBoot client() {
        if (clientBoot == null) {
            ClientBoot bootstrap = new ClientBoot();
            NioEventLoopGroup group = new NioEventLoopGroup();
            bootstrap
                    .group(group)
                    .channel(NioSocketChannel.class)
                    //最大连接时间
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                    //保持心跳
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    //开启Nagle算法，要求高实时性关闭，减少网络交互次数开启
                    .option(ChannelOption.TCP_NODELAY, true);
            GlobalEnvironmentContext.clientBoot = bootstrap;
        }
        clientBoot.handler(new ClientConnInitializer(currentIp.get()));
        return clientBoot;
    }

}
