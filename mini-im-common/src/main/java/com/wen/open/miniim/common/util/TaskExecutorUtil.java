package com.wen.open.miniim.common.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Wen
 * @date 2023/4/12 14:51
 */
@Slf4j
public class TaskExecutorUtil {

    private static final ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(10);

    public static void scheduleAtFixedRate(Runnable task, Long delay, Long duration, TimeUnit unit) {
        log.info("scheduleAtFixedRate 方法添加任务：" + LocalDateTime.now());
        threadPool.scheduleAtFixedRate(task, delay, duration, unit);
    }

}
