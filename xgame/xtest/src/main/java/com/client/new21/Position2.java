package com.client.new21;

public record Position2(int x, int y) {
    static void printSum(Object obj) {
        if (obj instanceof Position2(int x, int y)) {
            System.out.println(x+y);
        }
    }
}
