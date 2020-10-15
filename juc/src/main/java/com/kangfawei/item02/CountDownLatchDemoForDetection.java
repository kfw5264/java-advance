package com.kangfawei.item02;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemoForDetection {

    public static void main(String[] args) {
        // 需要采集100粉样品
        CountDownLatch latch = new CountDownLatch(100);
        System.out.println("开始采集样本......");

        for (int i = 0; i < 100; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "送来了第" + finalI + "份样品");
                } finally {
                    latch.countDown();
                }
            },  "T" + i).start();
        }


        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("样本采集完成，即将进行检测......");
    }
}
