package com.kangfawei.item02;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(100000);
        MyThread myThread = new MyThread(latch);

        for (int i = 0; i < 100000; i++) {
            new Thread(myThread::serviceWithLatch, "Thread" + i).start();
        }

        try {
            System.out.println("main await");
            latch.await();
            System.out.println("main finally");
            System.out.println("Latch--" + myThread.count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        MyThread myThread2 = new MyThread(latch);
        for (int i = 0; i < 100000; i++) {
            new Thread(myThread2::serviceNoLatch, "Thread" + i).start();
        }
        System.out.println("NoLatch--" + myThread2.count);
    }
}

class MyThread {
    private final CountDownLatch latch;
    public int count;

    public MyThread (CountDownLatch latch) {
        this.latch = latch;
    }

    public void serviceWithLatch() {
        try {
            synchronized (this) {
                count++;
            }
        } finally {
            latch.countDown();
        }
    }

    public void serviceNoLatch() {
        synchronized (this) {
            count++;
        }
    }
}
