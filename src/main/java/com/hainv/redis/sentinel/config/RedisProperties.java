package com.hainv.redis.sentinel.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "redis")
public class RedisProperties {
    private Integer maxIdle;
    private Integer minIdle;
    private Integer maxTotal;
    private Long maxWaitMillis;
    private Boolean testOnBorrow;
    private Integer timeout;
    private Integer db;
    private String password;
    private String sentinelMaster;
    private String sentinelNodes;
}
