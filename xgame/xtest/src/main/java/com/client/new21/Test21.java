package com.client.new21;

import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Test21 {
    public static void main(String[] args) throws InterruptedException {
        var list = new ArrayList<Integer>();
        for (int i = 0; i < 3; i++) {
            list.add(i);
        }
        System.err.println(list);

        System.err.println(formatterPatternSwitch(10));
        System.err.println(formatterPatternSwitch("abc"));
        System.err.println(formatterPatternSwitch(new Position2(2, 3)));


        System.err.println("==============");
        PP pp = new PP(10, 29);
        System.err.println(pp);

        System.err.println("==============");

        printSum1(new PP(11, 21));
        System.err.println("==============end");
        t1();
        Thread.sleep(10000);
    }

    static  void t1(){
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 10000).forEach(i -> {
                executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    return i;
                });
            });
        }  // executor.close() is called implicitly, and waits

    }

    static String formatterPatternSwitch(Object obj) {
        return switch (obj) {
            case Integer i -> String.format("int %d", i);
            case Long l -> String.format("long %d", l);
            case Double d -> String.format("double %f", d);
            case String s -> String.format("String %s", s);
            case Position2(int x, int y) -> String.format("String %s,String %s", x, y);
            default -> obj.toString();
        };
    }

    record PP(int x, int y) {
    }
    record PP1(int x, int y,int z) {
    }

    static void printSum(Object obj) {
        if (obj instanceof PP p) {
            int x = p.x();
            int y = p.y();
            System.err.println(x + y);
        }
    }

    static void printSum1(Object obj) {
        if (obj instanceof PP(int x,int y)) {
            System.err.println(x + y);
        }
    }
}
