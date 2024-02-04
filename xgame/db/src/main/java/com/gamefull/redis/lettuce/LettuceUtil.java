package com.gamefull.redis.lettuce;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.ReadFrom;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.Executions;
import io.lettuce.core.cluster.api.sync.NodeSelection;
import io.lettuce.core.cluster.api.sync.NodeSelectionCommands;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import io.lettuce.core.codec.StringCodec;
import io.lettuce.core.masterreplica.MasterReplica;
import io.lettuce.core.masterreplica.StatefulRedisMasterReplicaConnection;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.async.RedisPubSubAsyncCommands;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import io.lettuce.core.support.ConnectionPoolSupport;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * lettuce工具类
 * @author zcj
 */
public class LettuceUtil {
    public static final String URL="redis-12092.c309.us-east-2-1.ec2.cloud.redislabs.com";
    public static final int PORT=12092;

    public static final String PWD="fMjpJwTAkdvIOZQ3bvIhHdjezNMRWBGC";

    public static void main(String[] args) {
        try {
            test12();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     *Cluster 集群模式配置
     */
    private static void test13() throws InterruptedException{

    }
    /**
     *Cluster 集群模式配置
     */
    private static void test12() throws InterruptedException{
        Set<RedisURI> uris = new HashSet<>();
        uris.add(RedisURI.builder().withHost(URL).withPort(PORT).withPassword(PWD).build());
//        uris.add(RedisURI.builder().withHost("192.168.31.112").withPort(7000).withPassword("123456").build());
//        uris.add(RedisURI.builder().withHost("192.168.31.113").withPort(7000).withPassword("123456").build());
//        uris.add(RedisURI.builder().withHost("192.168.31.114").withPort(7000).withPassword("123456").build());
//        uris.add(RedisURI.builder().withHost("192.168.31.115").withPort(7000).withPassword("123456").build());
//        uris.add(RedisURI.builder().withHost("192.168.31.116").withPort(7001).withPassword("123456").build());

        RedisClusterClient client = RedisClusterClient.create(uris);
        StatefulRedisClusterConnection<String, String> connection = client.connect();
        RedisAdvancedClusterCommands<String, String> commands = connection.sync();
        commands.set("name", "关羽");
        System.out.println(commands.get("name"));

        //选择从节点,只读
        NodeSelection<String, String> replicas = commands.replicas();
        NodeSelectionCommands<String, String> nodeSelectionCommands = replicas.commands();
        Executions<List<String>> keys = nodeSelectionCommands.keys("*");
        keys.forEach(key -> System.out.println(key));

        connection.close();
        client.shutdown();
    }
    /**
     *哨兵模式配置
     */
    private static void test11() throws InterruptedException{
        //集群节点
        List<RedisURI> uris = new ArrayList();
        uris.add(RedisURI.builder().withSentinel("192.168.31.111", 26379).withSentinelMasterId("mymaster").withPassword("123456").build());
        uris.add(RedisURI.builder().withSentinel("192.168.31.112", 26379).withSentinelMasterId("mymaster").withPassword("123456").build());
        uris.add(RedisURI.builder().withSentinel("192.168.31.113", 26379).withSentinelMasterId("mymaster").withPassword("123456").build());

        RedisClient client = RedisClient.create();
        StatefulRedisMasterReplicaConnection<String, String> connection = MasterReplica.connect(client, StringCodec.UTF8, uris);
        //从节点读取数据
        connection.setReadFrom(ReadFrom.REPLICA);

        RedisCommands<String, String> commands = connection.sync();
        commands.set("name", "赵云");
        System.out.println(commands.get("name"));

        connection.close();
        client.shutdown();
    }
    /**
     *主从模式配置(手动指定集群节点)
     */
    private static void test10() throws InterruptedException{
        //集群节点
        List<RedisURI> uris = new ArrayList<>();
        uris.add(RedisURI.builder().withHost("192.168.31.111").withPort(6379).withPassword("111111").build());
        uris.add(RedisURI.builder().withHost("192.168.31.112").withPort(6379).withPassword("111111").build());
        uris.add(RedisURI.builder().withHost("192.168.31.113").withPort(6379).withPassword("111111").build());

        RedisClient client = RedisClient.create();
        StatefulRedisMasterReplicaConnection<String, String> connection = MasterReplica.connect(client, StringCodec.UTF8, uris);
        //从节点读取数据
        connection.setReadFrom(ReadFrom.REPLICA);

        RedisCommands<String, String> commands = connection.sync();
        commands.set("name", "张飞");
        System.out.println(commands.get("name"));

        connection.close();
        client.shutdown();
    }
    /**
     *主从模式配置
     */
    private static void test9() throws InterruptedException{
        //这里只需要配置一个节点的连接信息，不一定需要是主节点的信息，从节点也可以;可以自动发现主从节点
        RedisURI uri = RedisURI.builder().withHost(URL).withPort(PORT).withPassword(PWD).withTimeout(Duration.of(5, ChronoUnit.SECONDS)).build();
        RedisClient client = RedisClient.create(uri);
        StatefulRedisMasterReplicaConnection<String, String> connection = MasterReplica.connect(client, StringCodec.UTF8, uri);
        //从节点读取数据
        connection.setReadFrom(ReadFrom.REPLICA);

        RedisCommands<String, String> commands = connection.sync();
        commands.set("name", "张飞");
        System.out.println(commands.get("name"));

        connection.close();
        client.shutdown();
    }
    /**
     * 线程池配置
     */
    private static void test8() throws Exception{
        RedisURI uri = RedisURI.builder().withHost(URL).withPort(PORT).withPassword(PWD)
                .withTimeout(Duration.of(5, ChronoUnit.SECONDS)).build();
        RedisClient client = RedisClient.create(uri);
        //连接池配置
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(2);

        GenericObjectPool<StatefulRedisConnection<String, String>> pool = ConnectionPoolSupport.createGenericObjectPool(client::connect, poolConfig);
        StatefulRedisConnection<String, String> connection = pool.borrowObject();
        RedisCommands<String, String> commands = connection.sync();
        commands.set("name", "张飞");
        System.out.println(commands.get("name"));

        connection.close();
        pool.close();
        client.shutdown();
    }
    /**
     * 集群环境下，具体的配置案例
     */
    private static void test7() throws InterruptedException{
        ClientResources resources = DefaultClientResources.builder()
                .ioThreadPoolSize(4) //I/O线程数
                .computationThreadPoolSize(4) //任务线程数
                .build();
        RedisURI uri = RedisURI.builder().withHost(URL).withPort(PORT).withPassword(PWD)
                .withTimeout(Duration.of(5, ChronoUnit.SECONDS)).build();
        ClusterClientOptions options = ClusterClientOptions.builder()
                .autoReconnect(true)//是否自动重连
                .pingBeforeActivateConnection(true)//连接激活之前是否执行PING命令
                .validateClusterNodeMembership(true)//是否校验集群节点的成员关系
                .build();
        RedisClusterClient client = RedisClusterClient.create(resources, uri);
        client.setOptions(options);
        StatefulRedisClusterConnection<String, String> connection = client.connect();
        RedisAdvancedClusterCommands<String, String> commands = connection.sync();
        commands.set("name", "张飞");
        System.out.println(commands.get("name"));

        connection.close();
        client.shutdown();
        resources.shutdown();
    }
    /**
     * 客户端资源与参数配置
     * @throws InterruptedException
     */
    private static void test6() throws InterruptedException{
        ClientResources resources = DefaultClientResources.builder()
                .ioThreadPoolSize(4) //I/O线程数
                .computationThreadPoolSize(4) //任务线程数
                .build();
        RedisURI uri = RedisURI.builder().withHost(URL).withPort(PORT).withPassword(PWD)
                .withTimeout(Duration.of(5, ChronoUnit.SECONDS)).build();
        ClientOptions options = ClientOptions.builder()
                .autoReconnect(true)//是否自动重连
                .pingBeforeActivateConnection(true)//连接激活之前是否执行PING命令
                .build();
        RedisClient client = RedisClient.create(resources, uri);
        client.setOptions(options);
        StatefulRedisConnection<String, String> connection = client.connect();
        RedisCommands<String, String> commands = connection.sync();
        commands.set("name", "关羽");
        System.out.println(commands.get("name"));

        connection.close();
        client.shutdown();
        resources.shutdown();
    }
    /**
     * 发布和订阅
     * @throws InterruptedException
     */
    private static void test5() throws InterruptedException{
        RedisURI uri = RedisURI.builder().withHost(URL).withPort(PORT).withPassword(PWD)
                .withTimeout(Duration.of(5, ChronoUnit.SECONDS)).build();
        RedisClient redisClient = RedisClient.create(uri);
        //获取发布订阅操作命令工具
        StatefulRedisPubSubConnection<String, String> pubsubConn = redisClient.connectPubSub();
        pubsubConn.addListener(new RedisPubSubListener<String, String>() {
            @Override
            public void unsubscribed(String channel, long count) {
                System.out.println("[unsubscribed]" + channel);
            }
            @Override
            public void subscribed(String channel, long count) {
                System.out.println("[subscribed]" + channel);
            }
            @Override
            public void punsubscribed(String pattern, long count) {
                System.out.println("[punsubscribed]" + pattern);
            }
            @Override
            public void psubscribed(String pattern, long count) {
                System.out.println("[psubscribed]" + pattern);
            }
            @Override
            public void message(String pattern, String channel, String message) {
                System.out.println("[message]" + pattern + " -> " + channel + " -> " + message);
            }
            @Override
            public void message(String channel, String message) {
                System.out.println("[message]" + channel + " -> " + message);
            }
        });
        RedisPubSubAsyncCommands<String, String> pubsubCmd = pubsubConn.async();
        pubsubCmd.psubscribe("CH");
        pubsubCmd.psubscribe("CH2");
        pubsubCmd.unsubscribe("CH");

        Thread.sleep(100 * 5);
        pubsubConn.close();
        redisClient.shutdown();
    }

    /**
     * 响应式编程
     * @throws InterruptedException
     */
    private static void test4() throws InterruptedException {
        RedisURI uri = RedisURI.builder().withHost(URL).withPort(PORT).withPassword(PWD)
                .withTimeout(Duration.of(5, ChronoUnit.SECONDS)).build();
        RedisClient client = RedisClient.create(uri);
        StatefulRedisConnection<String,String> conn = client.connect();
        RedisReactiveCommands<String,String> commands = conn.reactive();
        Mono<String> setc = commands.set("name", "mayun");
        System.out.println(setc.block());
        Mono<String> getc = commands.get("name");
        getc.subscribe(System.out::println);
        Flux<String> keys = commands.keys("*");
        keys.subscribe(System.out::println);

        //开启一个事务，先把count设置为1，再将count自增1
        commands.multi().doOnSuccess(r -> {
                    commands.set("count", "1").doOnNext(value -> System.out.println("count1：" +  value)).subscribe();
                    commands.incr("count").doOnNext(value -> System.out.println("count2：" +  value)).subscribe();
//                    commands.keys("*").doOnNext(value -> System.out.println("kk：" +  value)).subscribe();
                }).flatMap(s -> commands.exec())
                .doOnNext(transactionResult -> System.out.println("transactionResult：" + transactionResult.wasDiscarded())).subscribe();

        Thread.sleep(1000 * 5);
        conn.close();
        client.shutdown();

    }

    /**
     * 异步操作
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void test3() throws ExecutionException, InterruptedException {
        RedisURI uri = RedisURI.builder().withHost(URL).withPort(PORT).withPassword(PWD)
                .withTimeout(Duration.of(5, ChronoUnit.SECONDS)).build();
        RedisClient client = RedisClient.create(uri);
        StatefulRedisConnection<String,String> conn = client.connect();
        RedisAsyncCommands<String,String> commands = conn.async();

        System.out.println("清空数据："+commands.flushdb().get());
        System.out.println("判断某个键是否存在："+commands.exists("username").get());
        System.out.println("新增<'username','xmr'>的键值对："+commands.set("username", "xmr").get());
        System.out.println("新增<'password','password'>的键值对："+commands.set("password", "123").get());
        System.out.println("获取<'password'>键的值："+commands.get("password").get());
        System.out.println("系统中所有的键如下：" + commands.keys("*").get());
        System.out.println("删除键password:"+commands.del("password").get());
        System.out.println("判断键password是否存在："+commands.exists("password").get());
        System.out.println("设置键username的过期时间为5s:"+commands.expire("username", 5L).get());
        System.out.println("查看键username的剩余生存时间："+commands.ttl("username").get());
        System.out.println("移除键username的生存时间："+commands.persist("username").get());
        System.out.println("查看键username的剩余生存时间："+commands.ttl("username").get());
        System.out.println("查看键username所存储的值的类型："+commands.type("username").get());

        conn.close();
        client.shutdown();
    }

    /**
     * 同步操作
     */
    private static void test2(){
        RedisURI uri = RedisURI.builder().withHost(URL).withPort(PORT).withPassword(PWD)
                .withTimeout(Duration.of(5, ChronoUnit.SECONDS)).build();
        RedisClient client = RedisClient.create(uri);
        StatefulRedisConnection<String,String> conn = client.connect();
        RedisCommands<String,String> commands = conn.sync();
        System.out.println("清空数据："+commands.flushdb());
        System.out.println("判断某个键是否存在："+commands.exists("username"));
        System.out.println("新增<'username','xmr'>的键值对："+commands.set("username", "xmr"));
        System.out.println("新增<'password','password'>的键值对："+commands.set("password", "123"));
        System.out.println("获取<'password'>键的值："+commands.get("password"));
        System.out.println("系统中所有的键如下：" + commands.keys("*"));
        System.out.println("删除键password:"+commands.del("password"));
        System.out.println("判断键password是否存在："+commands.exists("password"));
        System.out.println("设置键username的过期时间为5s:"+commands.expire("username", 5L));
        System.out.println("查看键username的剩余生存时间："+commands.ttl("username"));
        System.out.println("移除键username的生存时间："+commands.persist("username"));
        System.out.println("查看键username的剩余生存时间："+commands.ttl("username"));
        System.out.println("查看键username所存储的值的类型："+commands.type("username"));

        conn.close();
        client.shutdown();

    }

    /**
     * 测试是否能正常联通
     */
    private static void test1() {
        RedisURI uri = RedisURI.builder().withHost(URL).withPort(PORT).withPassword(PWD)
                .withTimeout(Duration.of(5, ChronoUnit.SECONDS)).build();
        RedisClient client = RedisClient.create(uri);
        StatefulRedisConnection<String,String> conn = client.connect();
        RedisCommands<String,String> cmd = conn.sync();
        System.err.println(cmd.ping());
        conn.close();
        client.shutdown();
    }


}
