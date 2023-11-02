//package com.xirui.redis.lettuce;
//
//import com.xirui.cache.util.JetCacheCodec;
//import io.lettuce.core.*;
//import io.lettuce.core.api.StatefulConnection;
//import io.lettuce.core.cluster.ClusterClientOptions;
//import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
//import io.lettuce.core.cluster.RedisClusterClient;
//import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
//import io.lettuce.core.masterreplica.MasterReplica;
//import io.lettuce.core.masterreplica.StatefulRedisMasterReplicaConnection;
//import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
//import org.apache.ibatis.mapping.CacheBuilder;
//
//import java.net.URI;
//import java.time.Duration;
//import java.util.ArrayList;
//import java.util.List;
//
//public class LettuceCfg {
//
//    public static final String url = "redis-12092.c309.us-east-2-1.ec2.cloud.redislabs.com:12092";
//    public static final String pwd ="fMjpJwTAkdvIOZQ3bvIhHdjezNMRWBGC";
//
//    public static final boolean pubsub = true;
//
//    public void init(){
//        AbstractRedisClient client;
//        StatefulConnection<byte[], byte[]> connection;
//        StatefulRedisPubSubConnection<byte[], byte[]> pubSubConnection = null;
//        List<RedisURI> uriList = new ArrayList<>();
//        uriList.add( RedisURI.create(URI.create(url)));
//        if ("Cluster".equalsIgnoreCase("Cluster")) {
//            client = RedisClusterClient.create(uriList);
//            connection = clusterConnection((RedisClusterClient) client, false);
//            if (pubsub) {
//                pubSubConnection = (StatefulRedisPubSubConnection) clusterConnection( (RedisClusterClient) client, true);
//            }
//        }else{
//            client = RedisClient.create();
//            ((RedisClient) client).setOptions(ClientOptions.builder().
//                    disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS).build());
//            StatefulRedisMasterReplicaConnection c = MasterReplica.connect(
//                    (RedisClient) client, new JetCacheCodec(), uriList);
//
//            connection = c;
//            if (pubsub) {
//                pubSubConnection = ((RedisClient) client).connectPubSub(new JetCacheCodec(), uriList.get(0));
//            }
//        }
//        ExternalCacheBuilder externalCacheBuilder = RedisLettuceCacheBuilder.createRedisLettuceCacheBuilder()
//                .connection(connection)
//                .pubSubConnection(pubSubConnection)
//                .redisClient(client)
//                .asyncResultTimeoutInMillis(asyncResultTimeoutInMillis);
//    }
//
//
//    protected String parseBroadcastChannel(ConfigTree ct) {
//        String broadcastChannel = ct.getProperty("broadcastChannel");
//        if (broadcastChannel != null && !"".equals(broadcastChannel.trim())) {
//            return broadcastChannel.trim();
//        } else {
//            return null;
//        }
//    }
//    private StatefulConnection<byte[], byte[]> clusterConnection( RedisClusterClient client, boolean pubsub) {
//        int enablePeriodicRefresh =60;
//        boolean enableAllAdaptiveRefreshTriggers =true;
//        ClusterTopologyRefreshOptions.Builder topologyOptionBuilder = ClusterTopologyRefreshOptions.builder();
//        if (enablePeriodicRefresh > 0) {
//            topologyOptionBuilder.enablePeriodicRefresh(Duration.ofSeconds(enablePeriodicRefresh));
//        }
//        if (enableAllAdaptiveRefreshTriggers) {
//            topologyOptionBuilder.enableAllAdaptiveRefreshTriggers();
//        }
//
//        ClusterClientOptions options = ClusterClientOptions.builder()
//                .topologyRefreshOptions(topologyOptionBuilder.build())
//                .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
//                .build();
//        client.setOptions(options);
//        if (pubsub) {
//            return client.connectPubSub(new JetCacheCodec());
//        } else {
//            StatefulRedisClusterConnection<byte[], byte[]> c = client.connect(new JetCacheCodec());
////            if (readFrom != null) {
////                c.setReadFrom(readFrom);
////            }
//            return c;
//        }
//    }
//}
