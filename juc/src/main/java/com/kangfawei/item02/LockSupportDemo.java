package com.kangfawei.item02;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            System.out.println("t1锁定.....");
            LockSupport.park();
            System.out.println("t1解锁");
        });

        // 验证先解锁再锁定线程不会等待而是直接往下进行
        Thread t2 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("t2锁定");
                LockSupport.park();
                System.out.println("t2已经解锁");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t3 = new Thread(() -> {
            try {
                LockSupport.unpark(t2);
                System.out.println("已经解锁t2");
                System.out.println("t3两秒后解锁t1");
                TimeUnit.SECONDS.sleep(2);
                LockSupport.unpark(t1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        t3.start();



    }
}
