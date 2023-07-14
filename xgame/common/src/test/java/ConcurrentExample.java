import redis.clients.jedis.Jedis;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentExample {
    private static final int MAX_CONCURRENT_TASKS = 5;
    private static final int TOTAL_TASKS = 20;
    private static final String SEMAPHORE_KEY = "my_semaphore";

    public static void main(String[] args) {
        Jedis jedis = new Jedis("dev-redisdb.nku4ud.clustercfg.memorydb.ap-southeast-1.amazonaws.com",6379); // Redis服务器地址
        jedis.flushDB(); // 清空Redis数据库

        DistributedSemaphore semaphore = new DistributedSemaphore(jedis, SEMAPHORE_KEY, MAX_CONCURRENT_TASKS);

        ExecutorService executorService = Executors.newFixedThreadPool(MAX_CONCURRENT_TASKS);

        for (int i = 0; i < TOTAL_TASKS; i++) {
            final int taskId = i;
            executorService.submit(() -> {
                try {
                    boolean acquired = semaphore.acquire();
                    if (acquired) {
                        System.out.println("Task " + taskId + " acquired the semaphore.");
                        // 执行需要同步的代码块
                        Thread.sleep(2000);
                        System.out.println("Task " + taskId + " released the semaphore.");
                        semaphore.release();
                    } else {
                        System.out.println("Task " + taskId + " failed to acquire the semaphore.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        executorService.shutdown();
    }
}
