package com.wen.open.miniim.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Wen
 * @date 2023/4/11 11:15
 */
@Configuration
@ConfigurationProperties(prefix = "config")
@Data
public class ImConfig {

    private Long broadDuration;

    private Integer broadPort;

    private Integer serverPort;

    private Boolean skipElse;

    private Boolean onlineConsole;

}
