import org.junit.jupiter.api.Test;

public class ThreadTest {

    @Test
    public void test(){
        Thread thread = new Thread(new SimpleThread());
        thread.start();
//        Thread.ofPlatform().name("thread-test").start(new SimpleThread());
    }

}
