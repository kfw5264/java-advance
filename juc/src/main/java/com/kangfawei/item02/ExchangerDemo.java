package com.kangfawei.item02;

import java.util.concurrent.Exchanger;

/**
 * Exchanger示例
 * @author kangfawei
 */
public class ExchangerDemo {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();

        new Thread(() -> {
            String str = "This is T1's data";
            System.out.println(Thread.currentThread().getName() + "---" + str);
            try {
                str = exchanger.exchange(str);
                System.out.println(Thread.currentThread().getName() + "---" + str);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();

        new Thread(() -> {
            String str = "This is T2's data";
            System.out.println(Thread.currentThread().getName() + "---" + str);
            try {
                str = exchanger.exchange(str);
                System.out.println(Thread.currentThread().getName() + "---" + str);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }
}
