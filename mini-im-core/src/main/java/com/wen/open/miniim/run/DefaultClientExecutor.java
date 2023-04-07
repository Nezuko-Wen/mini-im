package com.wen.open.miniim.run;

import com.wen.open.miniim.client.MiniClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author Wen
 * @date 2023/4/7 14:56
 */
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
