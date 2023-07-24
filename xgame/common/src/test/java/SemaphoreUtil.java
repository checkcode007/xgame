import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * redis 信号liang
 * @author zcj
 */
@Service
public class SemaphoreUtil {

    public static void init(RedissonClient redissonClient,String k,int maxLimit){
//        redissonClient.getSemaphore()
        redissonClient.getSemaphore(k).trySetPermits(maxLimit);
    }

    public static boolean tryAcq(RedissonClient redissonClient,String k,int permits){
        try {
           return redissonClient.getSemaphore(k).tryAcquire(permits,100, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return false;
    }
    public static int get(RedissonClient redissonClient,String k){
        try {
            return redissonClient.getSemaphore(k).availablePermits();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return 0;
    }
    public static void release(RedissonClient redissonClient,String k,int permits){
        try {
            redissonClient.getSemaphore(k).release(permits);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public static void del(RedissonClient redissonClient,String k){
        redissonClient.getKeys().delete(k);
    }
    public static void del11(RedissonClient redissonClient,String k){
        redissonClient.getSemaphore(k).delete();
    }
}
