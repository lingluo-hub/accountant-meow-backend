package com.meow.accountant.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableCaching
public class RedisConfig {

    @Bean("json")
    public RedisTemplate<Object, Object> redisTemplateJson(RedisConnectionFactory factory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean("base")
    public RedisTemplate<Object, Object> redisTemplateBase(RedisConnectionFactory factory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory factory) {
        Set<String> cacheNames = new HashSet<>();
        cacheNames.add("account");

        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        RedisCacheConfiguration accountConfig = RedisCacheConfiguration.defaultCacheConfig()
                // 设置缓存时间
                .entryTtl(Duration.ofSeconds(60))
                // 禁止缓存空数据
                .disableCachingNullValues();
        configMap.put("account", accountConfig);

        return RedisCacheManager.builder(factory)
                .cacheDefaults(accountConfig)
                .initialCacheNames(cacheNames)
                .withInitialCacheConfigurations(configMap)
                .build();
    }
}