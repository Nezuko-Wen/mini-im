package com.wen.open.miniim.core.client;

import com.wen.open.miniim.common.context.GlobalEnvironmentContext;
import com.wen.open.miniim.common.handler.ClientHandler;
import com.wen.open.miniim.common.handler.DiscoveryClientInHandler;
import com.wen.open.miniim.common.packentity.ServerBoot;
import com.wen.open.miniim.common.util.ConfigContextHolder;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author Wen
 * @date 2023/4/7 14:30
 */
@Component
public class MiniClient {
    public void startUdp() throws Exception {
        connectService();
        discoveryService();
    }

    private void connectService() {
        ServerBoot serverBoot = new ServerBoot();
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        serverBoot.group(boss, worker).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInboundHandlerAdapter() {
                    //服务器读写数据过程中的逻辑

                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        ByteBuf byteBuf = (ByteBuf) msg;
                        System.out.println(new Date() + ": 服务端读到数据 -> " + byteBuf.toString(StandardCharsets.UTF_8));
                    }
                });
        //服务器启动过程中的逻辑
        serverBoot.handler(new ChannelInitializer<NioServerSocketChannel>() {
            @Override
            protected void initChannel(NioServerSocketChannel ch) {
                System.out.println("服务端启动中");
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

    private void discoveryService() throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new ChannelInitializer<NioDatagramChannel>() {
                        @Override
                        protected void initChannel(NioDatagramChannel channel) {
                            channel.pipeline()
                                    .addLast(new DiscoveryClientInHandler())
                                    .addLast(new ClientHandler());
                        }
                    });
            b.bind(ConfigContextHolder.config().getBroadPort()).sync().channel().closeFuture().await();
        } finally {
            group.shutdownGracefully();
        }
    }

    private void bind(ServerBootstrap serverBootstrap, int port) {
        serverBootstrap.bind(port).addListener((future -> {
            if (future.isSuccess()) {
                System.out.printf("端口%s绑定成功%n", port);
                GlobalEnvironmentContext.server().bindPort(port);
            } else {
                System.out.printf("端口%s绑定失败%n", port);
                bind(serverBootstrap, port + 1);
            }
        }));
    }
}
