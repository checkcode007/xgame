package com.client.new21;

public class ScopedValueExample {
    final static ScopedValue<String> LoginUser = ScopedValue.newInstance();


    public static void main(String[] args) throws InterruptedException {
        ScopedValue.where(LoginUser, "Tom")
                .run(() -> {
                    new Service().login();
                });


        Thread.sleep(2000);
    }


    static class Service {
        void login() {
            System.out.println("user：" + LoginUser.get());
        }
    }
}