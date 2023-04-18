package com.wen.open.miniim.core.run;

import com.wen.open.miniim.common.context.GlobalEnvironmentContext;
import com.wen.open.miniim.common.util.TaskExecutorUtil;
import com.wen.open.miniim.core.client.MiniClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

/**
 * @author Wen
 * @date 2023/4/7 14:56
 */
@DependsOn("configContextHolder")
@Component
@Slf4j
public class DefaultClientExecutor {
    private final MiniClient client;

    public DefaultClientExecutor(MiniClient client) {
        this.client = client;
    }

    @PostConstruct
    public void start() throws Exception {
        client.startUdp();
        //扫描客户端
//        scanStart();
    }


    private void scanStart() {
        TaskExecutorUtil.scheduleAtFixedRate(() -> {
        }, 2L, 2L, TimeUnit.MINUTES);
    }

    @PreDestroy
    public void destroy() {
        GlobalEnvironmentContext.close();
    }

}
