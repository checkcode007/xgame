package com.client.new8;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Test {
    public static void main(String[] args) {
        try {
//            invokeAny3();
//            invokeAnyTimeout();
//            testInvokeAll();
//            testInvokeAllTimeout();
            testInvokeAllWhenInterrupt();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 如果线程在等待invokeAll()执行完成的时候,被中断,会抛出InterruptedException<br>
     * 此时线程池会终止没有完成的任务,这主要是为了减少资源的浪费.
     */
    public static void testInvokeAllWhenInterrupt() throws Exception
    {
        final ExecutorService executorService = Executors.newFixedThreadPool(5);

        // 调用invokeAll的线程
        Thread invokeAllThread = new Thread() {

            @Override
            public void run()
            {
                List<Callable<String>> tasks = new ArrayList<Callable<String>>();
                tasks.add(new SleepSecondsCallable("t1", 2));
                tasks.add(new SleepSecondsCallable("t2", 2));
                tasks.add(new RandomTenCharsTask());

                // 调用线程会阻塞,直到tasks全部执行完成(正常完成/异常退出)
                try
                {
                    List<Future<String>> results = executorService
                            .invokeAll(tasks);
                    System.out.println("wait for the result." + results.size());
                } catch (InterruptedException e)
                {
                    System.out
                            .println("I was wait,but my thread was interrupted.");
                    e.printStackTrace();
                }

            }
        };

        invokeAllThread.start();

        Thread.sleep(200);

        invokeAllThread.interrupt();

        executorService.shutdown();

    }
    /**
     * 可以通过Future.isCanceled()判断任务是被取消,还是完成(正常/异常)<br>
     * Future.isDone()总是返回true,对于invokeAll()的调用者来说,没有啥用
     */
    public static void testInvokeAllTimeout() throws Exception
    {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        List<Callable<String>> tasks = new ArrayList<Callable<String>>();
        tasks.add(new SleepSecondsCallable("t1", 2));
        tasks.add(new SleepSecondsCallable("t2", 2));
        tasks.add(new SleepSecondsCallable("t3", 3));
        tasks.add(new RandomTenCharsTask());

        List<Future<String>> results = executorService.invokeAll(tasks, 1,
                TimeUnit.SECONDS);

        System.out.println("wait for the result." + results.size());

        for (Future<String> f : results)
        {
            System.out.println("isCanceled=" + f.isCancelled() + ",isDone="
                    + f.isDone());
        }

        executorService.shutdown();

    }
    public static void testInvokeAll() throws Exception
    {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        List<Callable<String>> tasks = new ArrayList<Callable<String>>();
        tasks.add(new SleepSecondsCallable("t1", 2));
        tasks.add(new SleepSecondsCallable("t2", 2));
        tasks.add(new RandomTenCharsTask());
        tasks.add(new ExceptionCallable());

        // 调用该方法的线程会阻塞,直到tasks全部执行完成(正常完成/异常退出)
        List<Future<String>> results = executorService.invokeAll(tasks);

        // 任务列表中所有任务执行完毕,才能执行该语句
        System.out.println("wait for the result." + results.size());

        executorService.shutdown();

        for (Future<String> f : results)
        {
            // isCanceled=false,isDone=true
            System.out.println("isCanceled=" + f.isCancelled() + ",isDone="
                    + f.isDone());

            // ExceptionCallable任务会报ExecutionException
            System.out.println("task result=" + f.get());
        }
    }
    /**
     * 还没有到超时之前,所以的任务都已经异常完成,抛出ExecutionException<br>
     * 如果超时前满,还没有没有完成的任务,抛TimeoutException
     */
    public static void invokeAnyTimeout() throws Exception
    {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        List<Callable<String>> tasks = new ArrayList<Callable<String>>();

        tasks.add(new ExceptionCallable());
        tasks.add(new ExceptionCallable());
        tasks.add(new ExceptionCallable());
        tasks.add(new ExceptionCallable());
        tasks.add(new SleepSecondsCallable("t1", 5));
        String result = executorService.invokeAny(tasks, 3, TimeUnit.SECONDS);

        System.out.println("result=" + result);

        executorService.shutdown();
    }
    /**
     * 有异常的任务,有正常的任务,invokeAny()不会抛异常,返回最先正常完成的任务
     */
    public static void invokeAny3() throws Exception
    {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        List<Callable<String>> tasks = new ArrayList<Callable<String>>();

        tasks.add(new ExceptionCallable());
        tasks.add(new ExceptionCallable());
        tasks.add(new ExceptionCallable());
        tasks.add(new ExceptionCallable());

        tasks.add(new SleepSecondsCallable("t1", 2));

        String result = executorService.invokeAny(tasks);

        System.out.println("result=" + result);
        executorService.shutdown();
    }
    /**
     * 没有1个正常完成的任务,invokeAny()方法抛出ExecutionException,封装了任务中元素的异常
     *
     */
    public static void invokeAny2() throws Exception
    {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        List<Callable<String>> tasks = new ArrayList<Callable<String>>();

        tasks.add(new ExceptionCallable());
        tasks.add(new ExceptionCallable());
        tasks.add(new ExceptionCallable());

        String result = executorService.invokeAny(tasks);

        System.out.println("result=" + result);

        executorService.shutdown();
    }
    /**
     * 提交的任务集合,一旦有1个任务正常完成(没有抛出异常),会终止其他未完成的任务
     */
    public static void invokeAny1() throws Exception
    {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        List<Callable<String>> tasks = new ArrayList<>();

        tasks.add(new SleepSecondsCallable("t1", 2));
        tasks.add(new SleepSecondsCallable("t2", 1));

        String result = executorService.invokeAny(tasks);

        System.out.println("result=" + result);

        executorService.shutdown();
    }

}
