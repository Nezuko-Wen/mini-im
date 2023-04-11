package com.wen.open.miniim.core.run;

import com.wen.open.miniim.core.client.MiniClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

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
    }
}
