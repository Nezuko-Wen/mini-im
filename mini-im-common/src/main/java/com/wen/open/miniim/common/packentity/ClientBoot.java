package com.wen.open.miniim.common.packentity;

import com.wen.open.miniim.common.context.GlobalEnvironmentContext;
import io.netty.bootstrap.Bootstrap;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Wen
 * @date 2023/4/12 14:56
 */
public class ClientBoot extends Bootstrap {

    public void connect(int port, String host) {
        connect(this, host, port, 5);
    }


    private synchronized void connect(Bootstrap bootstrap, String host, int port, int retry) {
        if (GlobalEnvironmentContext.liveChannel.containsKey(host)) {
            return;
        }
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(host + ":连接成功!");
            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接！");
            } else { // 第几次重连
                int order = (5 - retry) + 1; // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重 连......");
                bootstrap.config().group().schedule(()
                        -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }
}
