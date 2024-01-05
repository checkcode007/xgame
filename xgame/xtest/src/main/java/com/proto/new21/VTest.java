package com.proto.new21;

public class VTest {
    public static void main(String[] args) throws Exception {
        t1();
    }
    static void t1(){
        try {

            int number = Integer.parseInt("1");
        } catch (NumberFormatException e) {

            System.err.println("Not a number");
        }
    }
    static void t2(){
        try {

            int number = Integer.parseInt("1");
        } catch (NumberFormatException _) {

            System.err.println("Not a number");
        }
    }
    static void t3(){
        Object object = null;
        if (object instanceof Points(int x, int _)) {

            System.out.println("object is a position, x = " + x);
        }
    }
    static  void t4(){
        Object obj = null;
        switch (obj) {
            case Byte    _ -> System.out.println("Integer number");
            case Short   _ -> System.out.println("Integer number");
            case Integer _ -> System.out.println("Integer number");
            case Long    _ -> System.out.println("Integer number");

            case Float  _ -> System.out.println("Floating point number");
            case Double _ -> System.out.println("Floating point number");

            default -> System.out.println("Not a number");
        }
    }

}
