package com.kangfawei.item03;

import com.kangfawei.utils.JUCUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author muaishenqiu
 */
public class FixedThreadPoolDemo {

    private static class MyTask implements Runnable {

        @Override
        public void run() {
            System.out.println("FIXED--start:" + Thread.currentThread().getId());
            JUCUtil.sleep(TimeUnit.SECONDS, 1);
            System.out.println("FIXED--stop:" + Thread.currentThread().getId());
        }
    }
    private static class MyTask2 implements Runnable {

            @Override
            public void run() {
                System.out.println("CACHED--start:" + Thread.currentThread().getId());
                JUCUtil.sleep(TimeUnit.SECONDS, 1);
                System.out.println("CACHED--stop:" + Thread.currentThread().getId());
            }
        }

    public static void main(String[] args) {
        MyTask task = new MyTask();
        MyTask2 task2 = new MyTask2();
        // 固定数量的线程池
        ExecutorService es = Executors.newFixedThreadPool(5);
        // 不限数量的线程池
        ExecutorService es2 = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            es.submit(task);
        }


        for (int i = 0; i < 10; i++) {
            es2.submit(task2);
        }
    }
}
