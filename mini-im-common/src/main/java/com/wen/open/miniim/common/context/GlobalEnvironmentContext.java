package com.wen.open.miniim.common.context;

import com.wen.open.miniim.common.handler.client.ClientConnInitializer;
import com.wen.open.miniim.common.packentity.ClientBoot;
import com.wen.open.miniim.common.packentity.ClientInfo;
import com.wen.open.miniim.common.packentity.ServerBoot;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundInvoker;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Wen
 * @date 2023/4/7 15:04
 */
@Slf4j
public class GlobalEnvironmentContext {

    /** 客户端的地址列表 */
    public static final ConcurrentHashMap<String, ClientInfo> onlineMap = new ConcurrentHashMap<>();

    public static final Set<String> liveChannel = new CopyOnWriteArraySet<>();

    //TODO 下线
    public static final Set<Channel> hungChannel = new CopyOnWriteArraySet<>();

    private static ServerBoot serverBoot;

    private static volatile boolean broad = true;

    /* 扫描客户端线程 */
    private static boolean scanExecutor = false;

    private static String localhost;

    private static ClientBoot clientBoot;

    private static final ThreadLocal<String> currentIp = new ThreadLocal<>();

    private static final ThreadLocal<Integer> currentPort = new ThreadLocal<>();

    private static Thread consoleThread;

    static {
        try {
            localhost = String.valueOf(InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            log.warn("获取主机名异常", e);
            localhost = "UNKNOWN";
        }
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
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ClientConnInitializer());
            clientBoot = bootstrap;
        }
        return clientBoot;
    }

    public static void register() {
        client().tryConnect(currentIp.get(), currentPort.get());
        clear();
    }

    public static void ip(String ip) {
        currentIp.set(ip);
    }

    private static void clear() {
        currentIp.remove();
        currentPort.remove();
    }

    public static void port(Integer port) {
        currentPort.set(port);
    }
    public static void serverLive(ServerBoot boot) {
        GlobalEnvironmentContext.serverBoot = boot;
    }

    public static ServerBoot server() {
        return serverBoot;
    }

    public static boolean holdBroad() {
        return broad;
    }

    public static void stopBroad() {
        broad = false;
    }

    public static void close() {
        //先停止传播
        stopBroad();
        //关闭通道
        try {
            hungChannel.forEach(ChannelOutboundInvoker::close);
        } finally {
            serverBoot.config().group().shutdownGracefully();
            serverBoot.config().childGroup().shutdownGracefully();
        }
        consoleThread.interrupt();
    }
    public static String localhost() {
        return localhost;
    }

    public static void console(Thread thread) {
        consoleThread = thread;
    }
}
