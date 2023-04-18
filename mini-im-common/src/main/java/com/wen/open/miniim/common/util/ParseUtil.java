package com.wen.open.miniim.common.util;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

/**
 * @author Wen
 * @date 2023/4/18 09:35
 */
public class ParseUtil {
    public static String tcpIp(ChannelHandlerContext ctx) {
        return ctx.channel().remoteAddress().toString().substring(1).split(":")[0];
    }

    public static String udpIp(DatagramPacket packet) {
        return String.valueOf(packet.sender()).replace("/", "").split(":")[0];
    }
}
