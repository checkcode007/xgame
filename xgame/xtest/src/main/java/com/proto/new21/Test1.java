package com.proto.new21;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;

public class Test1 {
    public static void main(String[] args) throws Exception {
        try (var scope = new StructuredTaskScope.ShutdownOnSuccess()) {
            StructuredTaskScope.Subtask res1 = scope.fork(() -> runTask(1));
            StructuredTaskScope.Subtask res2 = scope.fork(() -> runTask(2));
            StructuredTaskScope.Subtask res3 = scope.fork(() -> runTask(3));
            scope.join();
            System.out.println("scope:" + scope.result());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static String runTask(int i) throws InterruptedException {
        Thread.sleep(


                1000);
        long l = new Random().nextLong();
        String s = String.valueOf(l);
        System.out.println(i + "task：" + s);
        return s;
    }
}
