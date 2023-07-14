import redis.clients.jedis.Jedis;

public class DistributedSemaphore {
    private Jedis jedis;
    private String semaphoreKey;
    private int maxPermits;

    public DistributedSemaphore(Jedis jedis, String semaphoreKey, int maxPermits) {
        this.jedis = jedis;
        this.semaphoreKey = semaphoreKey;
        this.maxPermits = maxPermits;
    }

    public boolean acquire() {
        Long permits = jedis.incr(semaphoreKey);
        if (permits <= maxPermits) {
            return true;
        } else {
            jedis.decr(semaphoreKey);
            return false;
        }
    }

    public void release() {
        jedis.decr(semaphoreKey);
    }
}
