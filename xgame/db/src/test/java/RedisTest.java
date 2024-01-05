import com.proto.Managers;
import com.proto.redis.redisson.RedissonMgr;
import org.junit.jupiter.api.Test;
import org.redisson.api.*;

import java.io.Serializable;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

public class RedisTest {
    @Test
    public void test1(){
        Managers.ins.init();
        RedissonMgr redissonMgr = Managers.ins.getRedissonMgr();
        try {
            RBucket<Object> bucket= redissonMgr.getBucket("test:test1");
            System.err.println("get--------->"+bucket.get());
            bucket.set(15);
            System.err.println("get--------->"+bucket.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println("--------->");
        redissonMgr.close();
    }
    @Test
    public void test2(){
        Managers.ins.init();
        RedissonMgr redissonMgr = Managers.ins.getRedissonMgr();
        try {
            RBucket<Object> bucket= redissonMgr.getBucket("test:test1");
            System.err.println("get--------->"+bucket.get());
            bucket.set(21, Duration.ofSeconds(15));
            System.err.println("get--------->"+bucket.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println("--------->");
        redissonMgr.close();
    }

    @Test
    public void test3(){
        Managers.ins.init();
        RedissonMgr redissonMgr = Managers.ins.getRedissonMgr();
        try {
            RMap<String, String> rMap= redissonMgr.getMap("test:test2");
            System.err.println("get--------->"+rMap);
            rMap.put("1", "1");
            rMap.put("2", "2");
            System.err.println("get--------->"+rMap);
            RMap<Integer, Integer> rMap1= redissonMgr.getMap("test:test3");
            rMap1.put(1,11);
            rMap1.put(2,22);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println("--------->");
        redissonMgr.close();
    }
    @Test
    public void test4(){
        Managers.ins.init();
        RedissonMgr redissonMgr = Managers.ins.getRedissonMgr();
        try {
            RList<Integer> rList= redissonMgr.getList("test:test4");
            System.err.println("get--------->"+rList);
            for (int i = 10; i < 25; i++) {
                rList.add(0,i);
            }

            System.err.println("get--------->"+rList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println("--------->");
        redissonMgr.close();
    }
    @Test
    public void test5(){
        Managers.ins.init();
        RedissonMgr redissonMgr = Managers.ins.getRedissonMgr();
        try {
            RSortedSet<Integer> sortedSet= redissonMgr.getSortedSet("test:test5");
            System.err.println("get--------->"+sortedSet);
            for (int i = 10; i < 25; i++) {
                sortedSet.add(i);
            }

            System.err.println("get--------->"+sortedSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println("--------->");
        redissonMgr.close();
    }

    @Test
    public void test7() throws Exception{

        Managers.ins.init();

        RedissonMgr redissonMgr = Managers.ins.getRedissonMgr();
        try {
            RScoredSortedSet<Integer> sortedSet= redissonMgr.getScoredSortedSet("test:test7");
            System.err.println("get--------->"+ sortedSet.readAll());

            Set< RFuture<Boolean>> set = new HashSet<>();
            for (int i = 100; i < 105; i++) {
                set.add(sortedSet.addAsync(11+i,i));
            }
            for (RFuture<Boolean> future : set) {
                future.get();
            }

//            System.err.println("get--------->"+sortedSet.readAll());
//            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println("--------->");
        redissonMgr.close();
    }
    class  Student implements Serializable, Comparable<Student>{
        private Long id;

        private String name;

        private Integer age;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
        //get、set.....

        @Override
        public String toString() {
            return "Student{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }

        @Override
        public int compareTo(Student obj) {
            return this.getId().compareTo(obj.getId());
        }
    }
}
