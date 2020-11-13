package com.kangfawei.item02;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockInterrupt {
    public static void main(String[] args) {
        ReentrantLock lock1 = new ReentrantLock();
        ReentrantLock lock2 = new ReentrantLock();

        Thread t1 = new Thread(() -> {
            try {
                lock1.lock();
                System.out.println(Thread.currentThread().getName() + "获取到lock1");
                TimeUnit.SECONDS.sleep(1);
                lock2.lockInterruptibly();
                System.out.println(Thread.currentThread().getName() + "获取到lock2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if(lock1.isHeldByCurrentThread()) {
                    lock1.unlock();
                    System.out.println(Thread.currentThread().getName() + "解锁lock1");
                }
                if(lock2.isHeldByCurrentThread()) {
                    lock2.unlock();
                    System.out.println(Thread.currentThread().getName() + "解锁lock2");
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            try {
                lock2.lock();
                System.out.println(Thread.currentThread().getName() + "获取到lock2");
                TimeUnit.SECONDS.sleep(1);
                lock1.lockInterruptibly();
                System.out.println(Thread.currentThread().getName() + "获取到lock1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock2.unlock();
                System.out.println(Thread.currentThread().getName() + "解锁lock2");
                lock1.unlock();
                System.out.println(Thread.currentThread().getName() + "解锁lock1");
            }
        }, "t2");

        t1.start();
        t2.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t1.interrupt();
    }
}
