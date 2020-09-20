package com.kangfawei.item01;

import java.util.concurrent.TimeUnit;

/**
 * synchronized锁
 * @author kangfawei
 */
public class ThreadDemo03 {
    public static void main(String[] args) {
        SyncDemo syncDemo = new SyncDemo();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(syncDemo);
            thread.start();
        }

        UnSyncDemo unSyncDemo = new UnSyncDemo();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(unSyncDemo);
            thread.start();
        }

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(syncDemo.count);
        System.out.println(unSyncDemo.count);
    }

    private static class SyncDemo implements Runnable {
        int count = 0;

        @Override
        public void run() {
            synchronized (this) {
                for (int i = 0; i < 1000; i++) {
                    count++;
                }
            }
        }
    }

    private static class UnSyncDemo implements Runnable {
        int count = 0;

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                count++;
            }
        }
    }
}
