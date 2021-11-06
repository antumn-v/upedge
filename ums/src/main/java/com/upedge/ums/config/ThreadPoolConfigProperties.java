package com.upedge.ums.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "upedge.thread")
@Data
public class ThreadPoolConfigProperties {
    private Integer coreSize;

    private Integer maxSize;

    private Integer keepAliveTime;
}
