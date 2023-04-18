package com.wen.open.miniim.common.packentity;

import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Data;

/**
 * @author Wen
 * @date 2023/4/18 09:13
 */
@Data
public class ClientInfo {
    private String hostname;
    private NioSocketChannel channel;
}
