package com.kangfawei.item02;

import com.kangfawei.utils.JUCUtil;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockInterruptDemo implements Runnable {
    public static ReentrantLock lock1 = new ReentrantLock();
    public static ReentrantLock lock2 = new ReentrantLock();
    private int lock;

    public ReentrantLockInterruptDemo(int lock) {
        this.lock = lock;
    }

    public static void main(String[] args) {
        ReentrantLockInterruptDemo demo1 = new ReentrantLockInterruptDemo(1);
        ReentrantLockInterruptDemo demo2 = new ReentrantLockInterruptDemo(2);

        Thread t1 = new Thread(demo1, "t1");
        Thread t2 = new Thread(demo2, "t2");

        t1.start();
        t2.start();

        JUCUtil.sleep(TimeUnit.SECONDS, 10);

        t2.interrupt();
    }

    @Override
    public void run() {
        try {
            if(lock == 1) {
                lock1.lockInterruptibly();
                JUCUtil.sleep(TimeUnit.MICROSECONDS, 500);
                lock2.lockInterruptibly();
            } else {
                lock2.lockInterruptibly();
                JUCUtil.sleep(TimeUnit.MILLISECONDS, 500);
                lock1. lockInterruptibly();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(lock1.isHeldByCurrentThread()) {
                lock1.unlock();
            }
            if(lock2.isHeldByCurrentThread()) {
                lock2.unlock();
            }
            System.out.println(Thread.currentThread().getName() + "线程退出");
        }
    }


}
