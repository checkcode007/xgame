package com.gamefull.redis;


import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 分布式锁
 */
@Configuration
public class RedisConfig {

    @Value("${spring.redis.database.cluster.nodes:}")
    String clusterNodes;
    @Value("${spring.redis.database.host}")
    String host;
    @Value("${spring.redis.database.port}")
    int port;
    @Value("${spring.redis.database.password}")
    String password;
    @Value("${spring.redis.database.index}")
    int dbIndex;

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        RedisConfiguration  config1 = null;
        if (clusterNodes.contains("amazonaws")) {
            RedisClusterConfiguration config = new RedisClusterConfiguration();
            config.addClusterNode(RedisNode.fromString(clusterNodes));
            if (StringUtils.isNotBlank(password)){
                config.setPassword(password);
            }
            config1 =config;
        }else {
            RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
            config.setHostName(host);
            config.setPort(port);
            config.setDatabase(dbIndex);
            if (StringUtils.isNotBlank(password)){
                config.setPassword(password);
            }
            config1 =config;
        }
        LettuceConnectionFactory factory = new LettuceConnectionFactory(config1);
        // You can set other configuration options here if needed
        return factory;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public int getDbIndex() {
        return dbIndex;
    }

    public void setDbIndex(int dbIndex) {
        this.dbIndex = dbIndex;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
