package com.kangfawei.item02;

import com.kangfawei.utils.JUCUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLockInstance instance = new ReentrantLockInstance();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            threads.add(new Thread(instance :: increase));
        }

        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println(instance.count);

        for (int i = 0; i < 10; i++) {
            new Thread(instance :: tryLockDemo, "T" + i).start();
        }
    }
}

class ReentrantLockInstance {
    Integer count = 0;
    Integer count2 = 0;
    ReentrantLock lock = new ReentrantLock();
    public void increase () {
        for (int i = 0; i < 1000; i++) {
            try {
                lock.lock();
                count++;
            } finally {
                lock.unlock();
            }
        }
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        JUCUtil.sleep(TimeUnit.MILLISECONDS, 100);
    }

    public void tryLockDemo() {
        try {
            if(lock.tryLock(1, TimeUnit.SECONDS)) {
                try {
                    count2 ++;
                    TimeUnit.MILLISECONDS.sleep(200);
                    System.out.println(Thread.currentThread().getName() + "--" + count2);
                    System.out.println("===============================");
                } finally {
//                    lock.unlock();
                    // 先判读该线程是否获得锁
                    if(lock.isHeldByCurrentThread()) {
                        lock.unlock();
                    }
                }
            } else {
                System.out.println(Thread.currentThread().getName() + "没有获取到锁......");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
