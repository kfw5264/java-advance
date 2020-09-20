package com.kangfawei.item01;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Atomic原子类
 * @author kangfawei
 */
public class ThreadDemo06 {
    public static void main(String[] args) {
        AtomicDemo demo = new AtomicDemo();
        for (int i = 0; i < 10000; i++) {
            // lambda表达式
            new Thread(demo :: increaseCount).start();
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(demo.count);
    }
}

class AtomicDemo {
    // 通过AtomicInteger来解决线程安全问题，不用加锁，效率远高于synchronized
    AtomicInteger count = new AtomicInteger(0);

    public void increaseCount() {
        count.incrementAndGet();
    }
}
