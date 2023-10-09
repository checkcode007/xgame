public class SimpleThread implements Runnable{

    @Override
    public void run() {
        System.err.println("当前线程名称：" + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
