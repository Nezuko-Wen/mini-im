package com.wen.open.miniim.core.run;

import com.wen.open.miniim.common.util.TaskExecutorUtil;
import com.wen.open.miniim.core.client.MiniClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author Wen
 * @date 2023/4/7 14:56
 */
@DependsOn("configContextHolder")
@Component
public class DefaultClientExecutor implements InitializingBean {
    private final MiniClient client;

    public DefaultClientExecutor(MiniClient client) {
        this.client = client;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        client.startUdp();
        //扫描客户端
        scanStart();
    }

    private void scanStart() {
        TaskExecutorUtil.scheduleAtFixedRate(()->{}, 2L,2L, TimeUnit.MINUTES);
    }


}
