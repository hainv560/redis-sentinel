package com.hainv.redis.sentinel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.DefaultRedisCachePrefix;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCachePrefix;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.ArrayList;
import java.util.List;

@EnableConfigurationProperties(RedisProperties.class)
@Configuration
public class RedisConfiguration {

    private final RedisProperties redisProperties;

    @Autowired
    public RedisConfiguration(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return getLettuceConnectionFactory(redisProperties.getDb());
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }

    @Bean
    public CacheManager cacheManager() {
        RedisCacheManager manager = new RedisCacheManager(redisTemplate());
        RedisCachePrefix cachePrefix = new DefaultRedisCachePrefix("test");
        manager.setUsePrefix(true);
        manager.setCachePrefix(cachePrefix);
        manager.setDefaultExpiration(3600L);
        return manager;
    }

    private LettuceConnectionFactory getLettuceConnectionFactory(Integer db) {
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(sentinelConfig());
        lettuceConnectionFactory.setDatabase(db);
        lettuceConnectionFactory.setPassword(redisProperties.getPassword());
        lettuceConnectionFactory.setShareNativeConnection(true);
        return lettuceConnectionFactory;
    }

    private RedisSentinelConfiguration sentinelConfig() {
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration();
        sentinelConfig.setMaster(redisProperties.getSentinelMaster());
        String[] sentinels = redisProperties.getSentinelNodes().split("\\|");
        List<RedisNode> list = new ArrayList<>();
        for (String sentinel : sentinels) {
            String[] nodes = sentinel.split(":");
            list.add(new RedisNode(nodes[0], Integer.parseInt(nodes[1])));
        }
        sentinelConfig.setSentinels(list);
        return sentinelConfig;
    }
}
