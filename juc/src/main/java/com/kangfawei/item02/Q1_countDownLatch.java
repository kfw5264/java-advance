package com.kangfawei.item02;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Q1_countDownLatch {
    final static CountDownLatch latch1 = new CountDownLatch(1);
    final static CountDownLatch latch2 = new CountDownLatch(1);
    static Thread t1 = null, t2 = null;
    final static List<Integer> list = new ArrayList<>();

    public static void main(String[] args) {
        t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                if(i == 5) {
                    latch1.countDown();
                    try {
                        latch2.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                list.add(i);
                System.out.println("t1添加了第" + (i+1) + "个元素！");
            }
        });

        t2 = new Thread(() -> {
            System.out.println("t2启动");
            if(list.size() != 5) {
                try {
                    latch1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("------已经往集合中添加了五个元素了！-----");
            System.out.println("t2结束");
            latch2.countDown();
        });

        t2.start();
        t1.start();
    }
}