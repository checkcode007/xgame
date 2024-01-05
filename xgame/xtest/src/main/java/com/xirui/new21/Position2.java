package com.xirui.new21;

import java.awt.*;

public record Position2(int x, int y) {
    static void printSum(Object obj) {
        if (obj instanceof Position2(int x, int y)) {
            System.out.println(x+y);
        }
    }
}
