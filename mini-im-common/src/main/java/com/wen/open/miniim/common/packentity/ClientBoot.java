package com.wen.open.miniim.common.packentity;

import com.wen.open.miniim.common.context.GlobalEnvironmentContext;
import io.netty.bootstrap.Bootstrap;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Wen
 * @date 2023/4/12 14:56
 */
@Slf4j
public class ClientBoot extends Bootstrap {

    public void tryConnect(String host, int port) {
        connect(this, host, port, 5);
    }

    private synchronized void connect(Bootstrap bootstrap, String host, int port, int retry) {
        if (GlobalEnvironmentContext.liveChannel.contains(host)) {
            return;
        }
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("目标服务器:" + host + " Success!");
            } else if (retry == 0) {
                log.error("目标服务器:" + host + " Fail!");
            } else { // 第几次重连
                int order = (5 - retry) + 1; // 本次重连的间隔
                int delay = 1 << order;
                log.warn(new Date() + ": 连接失败，第" + order + "次重 连......");
                bootstrap.config().group().schedule(()
                        -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }
}
