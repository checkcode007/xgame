import jodd.util.ThreadUtil;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RedissTest {


    @Test
    public void test1(){
        RedisClusterConfig clusterConfig = new RedisClusterConfig();
        RedissonClient client =  clusterConfig.getRedissionClient();
        String k = "TEST:T1";
        client.getBucket(k).set("nihao",5, TimeUnit.MINUTES);

    }
    @Test
    public void test2(){
        RedisClusterConfig clusterConfig = new RedisClusterConfig();
        RedissonClient client =  clusterConfig.getRedissionClient();
        String k = "TEST:T2";
        SemaphoreUtil.init(client,k,5);
//        boolean b =  SemaphoreUtil.tryAcq(client,k,1);
//        System.err.println(b);
    }
    @Test
    public void test22(){
        RedisClusterConfig clusterConfig = new RedisClusterConfig();
        RedissonClient client =  clusterConfig.getRedissionClient();
        String k = "TEST:T2";
        SemaphoreUtil.release(client,k,1);
    }
    private static final String SEMAPHORE_KEY = "mySemaphore";
    private static final int THREAD_COUNT = 6;
    private static final int PERMITS_PER_THREAD = 5;
    @Test
    public void testttt(){
        try {
            RedisClusterConfig clusterConfig = new RedisClusterConfig();
            RedissonClient client =  clusterConfig.getRedissionClient();

            ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
            String k = "TEST:T2";
            for (int i = 0; i < THREAD_COUNT; i++) {
                executorService.execute(() -> {
//                    ThreadUtil.sleep(100);
                    boolean b =false;
                    try {
                        System.err.println("Thread " + Thread.currentThread().getId()+"====start"+"---leave:"+SemaphoreUtil.get(client,k));
                        b =SemaphoreUtil.tryAcq(client,k,1);
                        System.err.println("Thread " + Thread.currentThread().getId() + " acquired " + 1 + " permits"+"---leave:"+SemaphoreUtil.get(client,k)+"---->"+b);
                        // 在这里执行需要信号量控制的代码逻辑
                    } catch (Exception e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    } finally {
                        if(b){
                            System.err.println("Thread " + Thread.currentThread().getId() + " released start" + 1 + " permits"+"---leave:"+SemaphoreUtil.get(client,k));
                            SemaphoreUtil.release(client,k,1);
                            System.err.println("Thread " + Thread.currentThread().getId() + " released " + 1 + " permits"+"---leave:"+SemaphoreUtil.get(client,k));
                        }

                    }
                });
            }
            ThreadUtil.sleep(10000);
            executorService.shutdown();
        } finally {
        }
    }
    @Test
    public void test3(){
        RedisClusterConfig clusterConfig = new RedisClusterConfig();
        RedissonClient client =  clusterConfig.getRedissionClient();
        String k = "TEST:T2";
        SemaphoreUtil.del11(client,k);
    }
}
