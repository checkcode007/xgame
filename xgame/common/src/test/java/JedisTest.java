import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.ZParams;

import java.util.Set;

public class JedisTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("dev-redisdb.nku4ud.clustercfg.memorydb.ap-southeast-1.amazonaws.com",6379);
        String k ="{A1}:";
        // 添加集合1的成员和分数
        jedis.zadd(k+"set1", 1.0, "member1");
        jedis.zadd(k+"set1", 2.0, "member2");
        jedis.zadd(k+"set1", 3.0, "member3");
        jedis.zadd(k+"set1", 3.0, "member5");

        // 添加集合2的成员和分数
        jedis.zadd(k+"set2", 2.0, "member1");
        jedis.zadd(k+"set2", 1.0, "member2");
        jedis.zadd(k+"set2", 3.0, "member3");
        jedis.zadd(k+"set2", 4.0, "member4");

        // 创建ZParams对象
        ZParams params = new ZParams();

        // 设置交集操作参数
        params.aggregate(ZParams.Aggregate.SUM);  // 使用SUM进行聚合
        params.weights(1.0, 0);  // 设置权重

        // 执行ZINTERSTORE命令，计算交集并按权重排序
        String destinationKey = "testhb";
        String[] keys = {k+"set1", k+"set2"};
        jedis.zinterstore(k+"set1", params, keys);

        // 获取排序后的结果，包括成员和分数
        Set<Tuple> sortedMembersWithScores = jedis.zrangeWithScores(k+"set1", 0, -1);
        for (Tuple tuple : sortedMembersWithScores) {
            String member = tuple.getElement();
            double score = tuple.getScore();
            System.out.println(member + " - " + score);
        }

        // 关闭连接
        jedis.close();
    }
    public static void min(){
        Jedis jedis = new Jedis("dev-redisdb.nku4ud.clustercfg.memorydb.ap-southeast-1.amazonaws.com",6379);
        String k ="{TT_1}:";
        // 添加集合1的成员和分数
        jedis.zadd(k+"set1", 1.0, "member1");
        jedis.zadd(k+"set1", 2.0, "member2");
        jedis.zadd(k+"set1", 3.0, "member3");

        // 添加集合2的成员和分数
        jedis.zadd(k+"set2", 2.0, "member1");
        jedis.zadd(k+"set2", 1.0, "member2");
        jedis.zadd(k+"set2", 3.0, "member3");

        // 创建ZParams对象
        ZParams params = new ZParams();

        // 设置交集操作参数
        params.aggregate(ZParams.Aggregate.MIN);  // 使用SUM进行聚合
        params.weights(2.0, 1.0);  // 设置权重

        // 执行ZINTERSTORE命令，计算交集并按权重排序
        String destinationKey = "testhb";
        String[] keys = {k+"set1", k+"set2"};
        jedis.zinterstore(k+"set3", params, keys);

        // 获取排序后的结果，包括成员和分数
        Set<Tuple> sortedMembersWithScores = jedis.zrangeWithScores(k+"set3", 0, -1);
        for (Tuple tuple : sortedMembersWithScores) {
            String member = tuple.getElement();
            double score = tuple.getScore();
            System.out.println(member + " - " + score);
        }

        // 关闭连接
        jedis.close();
    }
    public static void sum(){
        Jedis jedis = new Jedis("dev-redisdb.nku4ud.clustercfg.memorydb.ap-southeast-1.amazonaws.com",6379);
        String k ="{TTT}:";
        // 添加集合1的成员和分数
        jedis.zadd(k+"set1", 1.0, "member1");
        jedis.zadd(k+"set1", 2.0, "member2");
        jedis.zadd(k+"set1", 3.0, "member3");

        // 添加集合2的成员和分数
        jedis.zadd(k+"set2", 2.0, "member1");
        jedis.zadd(k+"set2", 1.0, "member2");
        jedis.zadd(k+"set2", 3.0, "member3");

        // 创建ZParams对象
        ZParams params = new ZParams();

        // 设置交集操作参数
        params.aggregate(ZParams.Aggregate.SUM);  // 使用SUM进行聚合
        params.weights(2.0, 1.0);  // 设置权重

        // 执行ZINTERSTORE命令，计算交集并按权重排序
        String destinationKey = "testhb";
        String[] keys = {k+"set1", k+"set2"};
        jedis.zinterstore(k+"set3", params, keys);

        // 获取排序后的结果，包括成员和分数
        Set<Tuple> sortedMembersWithScores = jedis.zrangeWithScores(k+"set3", 0, -1);
        for (Tuple tuple : sortedMembersWithScores) {
            String member = tuple.getElement();
            double score = tuple.getScore();
            System.out.println(member + " - " + score);
        }

        // 关闭连接
        jedis.close();
    }
}
