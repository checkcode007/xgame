package com.proto.redis.lettuce;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisStringAsyncCommands;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.codec.StringCodec;
import java.io.InputStream;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class LettuceMgr {
    private static final String LETTUCE_CLUSTER_YML = "lettuce-cluster.yml";
    private static final String LETTUCE_SINGLE_YML = "lettuce-single.yml";

    private LettuceEnum single,cluster;
    public void init(){
        initSingle();
        initCluster();
    }
    private void initSingle() {
        RedisSingleConfig config = readSingleYaml(LETTUCE_SINGLE_YML);
        if (config != null) {
            // 使用配置信息进行操作...
            System.out.println("Redis sigle node: " + config.getNode());
            System.out.println("Password: " + config.getPassword());
            System.out.println("SSL Enabled: " + config.isSsl());
            System.out.println("Timeout: " + config.getTimeout() + " ms");
        }

        RedisURI node =RedisURI.create(config.getNode());
        RedisClient client = RedisClient.create(node);
        ClientOptions clientOptions = ClientOptions.builder().build();
        client.setOptions(clientOptions);
        StatefulRedisConnection connection = client.connect(StringCodec.UTF8);
        LettuceEnum.SINGLE.init(client,connection);
        single = LettuceEnum.SINGLE;
    }
    private void initCluster() {
        RedisClusterConfig config = readClusterYaml(LETTUCE_CLUSTER_YML);
        if (config != null) {
            // 使用配置信息进行操作...
            System.out.println("Redis Cluster Nodes: " + config.getNodes());
            System.out.println("Password: " + config.getPassword());
            System.out.println("SSL Enabled: " + config.isSsl());
            System.out.println("Timeout: " + config.getTimeout() + " ms");
        }
        List<String> clusterNodes = config.getNodes();

        RedisURI[] nodes = clusterNodes.stream().map(RedisURI::create).toArray(RedisURI[]::new);

        RedisClusterClient client = RedisClusterClient.create(Arrays.asList(nodes));
        ClusterClientOptions clientOptions = ClusterClientOptions.builder().build();
        client.setOptions(clientOptions);
        StatefulRedisClusterConnection connection = client.connect(StringCodec.UTF8);
        LettuceEnum.CLUSTER.init(client,connection);
        cluster = LettuceEnum.CLUSTER;
    }

    private static RedisClusterConfig readClusterYaml(String fileName) {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        try {
            InputStream inputStream = LettuceMgr.class.getClassLoader().getResourceAsStream(fileName);
            return  objectMapper.readValue(inputStream, RedisClusterConfig.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private static RedisSingleConfig readSingleYaml(String fileName) {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        try {
            InputStream inputStream = LettuceMgr.class.getClassLoader().getResourceAsStream(fileName);
            return  objectMapper.readValue(inputStream, RedisSingleConfig.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public LettuceEnum getSingle() {
        return single;
    }
    public LettuceEnum getCluster() {
        return cluster;
    }

    public static void main(String[] args) throws  Exception {
        LettuceMgr mgr = new LettuceMgr();
        mgr.init();
        LettuceEnum cluster = mgr.getSingle();

        // 使用 clusterClient 进行操作...
        RedisStringAsyncCommands<String, String> async = (RedisStringAsyncCommands<String, String>) cluster.asyncCommands();
        RedisFuture<String> set = async.set("test:ltest1", "1");
        RedisFuture<String> get = async.get("test:ltest1");
        boolean b =  LettuceFutures.awaitAll(Duration.ofSeconds(3),set, get);
        System.err.println("await--->"+b);

        System.err.println("set get======>"+set.get());
        System.err.println("get get======>"+set.get());
        // 关闭连接
        cluster.close();
    }

}
