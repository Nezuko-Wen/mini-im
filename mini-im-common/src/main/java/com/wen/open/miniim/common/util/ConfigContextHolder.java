package com.wen.open.miniim.common.util;

import com.wen.open.miniim.common.context.ConfigContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ConfigContextHolder implements ApplicationContextAware {
    private static ConfigContext context;

    public static ConfigContext config() {
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ConfigContextHolder.context = applicationContext.getBean(ConfigContext.class);
    }
}