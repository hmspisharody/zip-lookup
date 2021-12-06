package com.zip.ziplookup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {
        System.out.println("connecting with redis");
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("redis", 6379);
        return redisStandaloneConfiguration;
    }

    @Bean
    public RedisConnectionFactory connectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration) {

        JedisClientConfiguration configuration = JedisClientConfiguration.builder().build();

        return new JedisConnectionFactory(redisStandaloneConfiguration, configuration);
    }

    @Bean
    @Primary
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<?, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory(redisStandaloneConfiguration()));

        return template;

    }
}
