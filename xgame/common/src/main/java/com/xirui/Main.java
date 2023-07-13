package com.xirui;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        System.err.println(TimeUnit.DAYS.toSeconds(1));
        System.err.println(TimeUnit.DAYS.toSeconds(-1));
        System.err.println(TimeUnit.DAYS.toSeconds(2));
        System.err.println(TimeUnit.DAYS.toSeconds(30));


        System.err.println(TimeUnit.DAYS.toMillis(1));
        System.err.println(TimeUnit.DAYS.toMillis(2));
        System.err.println(TimeUnit.DAYS.toMillis(30));
    }
}