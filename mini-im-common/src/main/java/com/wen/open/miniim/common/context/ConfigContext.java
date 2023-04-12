package com.wen.open.miniim.common.context;

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
public class ConfigContext {

    private String defaultHostname;

    private Long broadDuration;

    private Integer broadPort;

    private Integer serverPort;

}
