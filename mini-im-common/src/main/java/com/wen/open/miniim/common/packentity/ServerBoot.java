package com.wen.open.miniim.common.packentity;

import io.netty.bootstrap.ServerBootstrap;
import lombok.Getter;

/**
 * @author Wen
 * @date 2023/4/12 14:01
 */
@Getter
public class ServerBoot extends ServerBootstrap {

    private int bindPort;

    public void bindPort(int bindPort) {
        this.bindPort = bindPort;
    }

    public int port() {
        return this.bindPort;
    }
}
