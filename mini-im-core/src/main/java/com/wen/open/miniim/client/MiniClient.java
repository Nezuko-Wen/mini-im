package com.wen.open.miniim.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author Wen
 * @date 2023/4/7 14:30
 */
public class MiniClient {

    private ConcurrentHashMap<String, DatagramPacket> cliMap = new ConcurrentHashMap<>(); // 保存FeiQ客户端的地址列表

    private void startUdp() throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true).handler(new ChannelInboundHandlerAdapter() {
                @Override
                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                    ctx.executor().parent().execute(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < 10; i++) {
                                ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("我在广播" + i,
                                        StandardCharsets.UTF_8), new InetSocketAddress("255.255.255.255", 10000)));
                                try {
                                    TimeUnit.SECONDS.sleep(2);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
                @Override
                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                    System.out.println("-------------cliList");
                    cliMap.keySet().forEach(System.out::println);
                    DatagramPacket packet = (DatagramPacket) msg;
                    ByteBuf byteBuf = packet.copy().content();
                    byte[] bytes = new byte[byteBuf.readableBytes()];
                    byteBuf.readBytes(bytes);
                    String content = new String(bytes);
                    System.out.println(packet.sender() + "," + content);
                    //在线客户端列表
                    cliMap.putIfAbsent(String.valueOf(packet.sender()), packet);
                }
            });
            b.bind(10000).sync().channel().closeFuture().await();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        MiniClient feiQClient = new MiniClient();
        feiQClient.startUdp();
    }
}
