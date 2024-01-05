package com.proto.redis.redisson;

import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;

import java.io.File;

/**
 * redisson 管理类
 * @author zcj
 */
public class BaseRedissonMgr {
    protected RedissonClient redissonClient;
    public BaseRedissonMgr() {
        init();
    }
    // 初始化 Redisson 连接
    public void init()  {
        try {
            String configFile ="redisson-config.yml";
            configFile = configFile.replace("/", File.separator);
            Config config = loadConfig(configFile);
            JsonJacksonCodec codec = new JsonJacksonCodec();
            config.setCodec(codec);
            redissonClient = Redisson.create(config);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 从配置文件加载 Redisson 配置
    private Config loadConfig(String configFile) {
        try {
            return Config.fromYAML(BaseRedissonMgr.class.getClassLoader().getResource(configFile));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load Redisson configuration.", e);
        }
    }

    public RedissonClient getRedissonClient() {
        if (redissonClient == null) {
            throw new IllegalStateException("Redisson client is not initialized. Call init method first.");
        }
        return redissonClient;
    }
    public RBucket<Object> getBucket(String key) {
        return redissonClient.getBucket(key);
    }

    // 获取分布式锁
    public RLock getLock(String lockName) {
        return redissonClient.getLock(lockName);
    }

    // 获取读锁
    public RReadWriteLock getReadWriteLock(String lockName) {
        return redissonClient.getReadWriteLock(lockName);
    }

    // 获取分布式原子Long
    public RAtomicLong getAtomicLong(String name) {
        return redissonClient.getAtomicLong(name);
    }

    // 获取分布式集合
    public <T> RSet<T> getSet(String name) {
        return redissonClient.getSet(name);
    }

    // 获取分布式队列
    public <T> RQueue<T> getQueue(String name) {
        return redissonClient.getQueue(name);
    }

    // 获取分布式主题
    public <T> RTopic getTopic(String name) {
        return redissonClient.getTopic(name);
    }

    // 获取分布式Map
    public <K, V> RMap<K, V> getMap(String name) {
        return redissonClient.getMap(name);
    }
    public <V> RList<V> getList(String name) {
        return redissonClient.getList(name);
    }
    public <V> RSortedSet<V> getSortedSet(String name) {
        return redissonClient.getSortedSet(name);
    }


    // 获取分布式有序集合
    public <V> RScoredSortedSet<V> getScoredSortedSet(String name) {
        return redissonClient.getScoredSortedSet(name);
    }

    // 关闭 Redisson 连接
    public void close() {
        if (redissonClient != null) {
            redissonClient.shutdown();
        }
    }
}
