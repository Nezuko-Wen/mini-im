package com.wen.open.miniim.common.util;

import java.net.SocketAddress;

/**
 * @author Wen
 * @date 2023/4/18 09:35
 */
public class ParseUtil {
    public static String ip(SocketAddress address) {
        return address.toString().substring(1).split(":")[0];
    }
}
