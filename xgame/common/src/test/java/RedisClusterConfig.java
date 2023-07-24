import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
public class RedisClusterConfig {
    @Bean
    public RedisClusterConfiguration redisClusterConfiguration() {

        RedisClusterConfiguration configuration = new RedisClusterConfiguration();
        // 设置Redis集群节点的配置
        configuration.clusterNode("dev-redisdb.nku4ud.clustercfg.memorydb.ap-southeast-1.amazonaws.com", 6379);
//        configuration.clusterNode("node2", 6379);
//        configuration.clusterNode("node3", 6379);
        // 添加其他配置（如果需要）
        // configuration.setPassword("password");
        // ...
        return configuration;
    }
    public Config getCfg() {
        Config config = new Config();
        ClusterServersConfig configuration =config.useClusterServers();
        // 设置Redis集群节点的配置
        configuration.addNodeAddress("redis://dev-redisdb.nku4ud.clustercfg.memorydb.ap-southeast-1.amazonaws.com:6379");
        return config;
    }
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory(redisClusterConfiguration());
        // 设置其他属性（如果需要）
        // factory.setTimeout(redisProperties.getTimeout());
        // factory.setPassword(redisProperties.getPassword());
        // ...
        return factory;
    }
    @Bean
    public RedissonClient getRedissionClient() {
        return Redisson.create(getCfg());
    }
}
