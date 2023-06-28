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
    /** 广播间隔时间*/
    private Long broadDuration;

    /** 广播端口*/
    private Integer broadPort;

    /** 服务端口*/
    private Integer serverPort;

    /** 广播是否跳过自身检验，true:会发现自身 false:不会发现自身*/
    private Boolean skipElse;

    /** 文件包分包大小*/
    private int fileSplitLength;
}
