package com.wen.open.miniim.common.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.nio.charset.StandardCharsets;

/**
 * @author Wen
 * @date 2023/4/12 16:21
 */
public class LogTestUtil {

    public static ByteBuf write(String msg) {
        // 1.获取二进制抽象byteBuf
        ByteBuf buffer = ByteBufAllocator.DEFAULT.ioBuffer();
        // 2.准备数据，指定字符串的字符集为 utf-8
        byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
        // 3.填充数据到 ByteBuf
        buffer.writeBytes(bytes);
        return buffer;
    }
}
