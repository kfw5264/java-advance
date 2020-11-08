package com.kangfawei.item02;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        Counter counter = new Counter();
        for (int i = 0; i < 10; i++) {
            new Thread(counter :: increase).start();
        }
        for (int i = 0; i < 100; i++) {
            new Thread(counter :: get).start();
        }

    }



    private static final class Counter {
        private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
        private final Lock rLock = rwLock.readLock();
        private final Lock wLock = rwLock.writeLock();
        private int count = 0;

        public void increase() {
            wLock.lock();
            try {
                count++;
                System.out.println("count增加");
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                wLock.unlock();

            }


        }

        public void get() {
            rLock.lock();
            try{
                System.out.println(count);
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
              rLock.unlock();
            }
        }
    }
}
