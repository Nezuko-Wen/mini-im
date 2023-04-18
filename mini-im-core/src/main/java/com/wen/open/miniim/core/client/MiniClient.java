package com.wen.open.miniim.core.client;

import com.wen.open.miniim.StartApplication;
import com.wen.open.miniim.common.context.ConfigContextHolder;
import com.wen.open.miniim.common.context.GlobalEnvironmentContext;
import com.wen.open.miniim.common.handler.broad.DiscoveryInitializer;
import com.wen.open.miniim.common.handler.command.MessageHandler;
import com.wen.open.miniim.common.handler.server.ServerChildInitializer;
import com.wen.open.miniim.common.packentity.ClientInfo;
import com.wen.open.miniim.common.packentity.ServerBoot;
import com.wen.open.miniim.common.packet.MessagePacket;
import com.wen.open.miniim.core.excutor.ConsoleThread;
import io.netty.bootstrap.AbstractBootstrap;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Scanner;

/**
 * @author Wen
 * @date 2023/4/7 14:30
 */
@Component
@Slf4j
public class MiniClient {
    public void startUdp() {
        //开启服务端口
        connectService();
        //开始传播发现其他客户端
        discoveryService();
    }

    private void connectService() {
        ServerBoot serverBoot = new ServerBoot();
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup();
        serverBoot.group(boss, worker).channel(NioServerSocketChannel.class)
                .childHandler(new ServerChildInitializer());
        //服务器启动过程中的逻辑
        serverBoot.handler(new ChannelInitializer<NioServerSocketChannel>() {
            @Override
            protected void initChannel(NioServerSocketChannel ch) {
                log.info("服务端启动中");
            }
        });
        //给连接设置tcp属性
        serverBoot.childOption(ChannelOption.SO_KEEPALIVE, true)//开启心跳
                .childOption(ChannelOption.TCP_NODELAY, true);//开启Nagle算法，要求高实时性关闭，减少网络交互次数开启
        //给服务端channel设置属性
        serverBoot.option(ChannelOption.SO_BACKLOG, 1024);//表示系统用于临时存放已完成三次握手的请求的队列的最大长度，如果连接建立频繁，服务器处理创建新连接
        GlobalEnvironmentContext.serverLive(serverBoot);
        bind(serverBoot, ConfigContextHolder.config().getServerPort());
    }

    private void discoveryService() {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new DiscoveryInitializer());
        bind(b, ConfigContextHolder.config().getBroadPort());
    }

    private void bind(AbstractBootstrap bootstrap, int port) {
        if (bootstrap instanceof ServerBootstrap) {
            ServerBootstrap tar = (ServerBootstrap) bootstrap;
            tar.bind(port).addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    log.info("端口%s绑定成功:{}", port);
                    GlobalEnvironmentContext.server().bindPort(port);
                    GlobalEnvironmentContext.hungChannel.add(future.channel());
                    startConsoleThread();
                } else {
                    log.warn("端口%s绑定失败:{}", port);
                    bind(tar, port + 1);
                }
            });
        } else if (bootstrap instanceof Bootstrap){
            Bootstrap tar = (Bootstrap) bootstrap;
            tar.bind(port).addListener(((ChannelFutureListener)future -> {
                if (future.isSuccess()) {
                    log.info("端口%s绑定成功:{}", port);
                    GlobalEnvironmentContext.hungChannel.add(future.channel());
                } else {
                    log.warn("无法传播:{}", port);
                    System.exit(0);
                }
            }));
        } else {
            throw new RuntimeException("未知BootStrap模型");
        }
    }

    private void startConsoleThread() {
        ConsoleThread consoleThread = new ConsoleThread();
        consoleThread.start();
        GlobalEnvironmentContext.console(consoleThread);
    }

}
