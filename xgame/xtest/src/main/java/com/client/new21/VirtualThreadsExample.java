package com.client.new21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VirtualThreadsExample {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor =Executors.newVirtualThreadPerTaskExecutor();
        for (int i = 0; i < 10; i++) {
            int taskId = i;
            executor.execute(() -> {
                System.out.println("Task " + taskId + " is running on virtual thread: " + Thread.currentThread().getName());
            });
        }

        Thread.sleep(10000);
        executor.shutdown();
        System.out.println("==============");
        Thread.startVirtualThread(() -> {
            System.out.println(Thread.currentThread());
        });

        Thread.ofVirtual().start(() -> {
            System.out.println(Thread.currentThread());
        });
        Thread.sleep(5000);
    }
}
