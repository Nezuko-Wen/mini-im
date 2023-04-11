package com.wen.open.miniim.common.context;

import io.netty.channel.socket.DatagramPacket;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Wen
 * @date 2023/4/7 15:04
 */
public class GlobalEnvironmentContext {

    /** 客户端的地址列表 */
    public static ConcurrentHashMap<String, String> cliMap = new ConcurrentHashMap<>();
}
