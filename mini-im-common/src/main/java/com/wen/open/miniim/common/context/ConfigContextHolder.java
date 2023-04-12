package com.wen.open.miniim.common.context;

import com.wen.open.miniim.common.config.ImConfig;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class ConfigContextHolder implements ApplicationContextAware {
    private static ImConfig context;

    public static ImConfig config() {
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ConfigContextHolder.context = applicationContext.getBean(ImConfig.class);
    }
}