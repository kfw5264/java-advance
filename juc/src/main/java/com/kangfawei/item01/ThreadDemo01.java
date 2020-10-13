package com.kangfawei.item01;

import com.kangfawei.utils.JUCUtil;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 创建线程的几种方法
 * @author kangfawei
 */
public class ThreadDemo01 {
    public static void main(String[] args) {
        new MyThread01().start();
        new Thread(new MyThread02()).start();
    }
}

class MyThread01 extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            JUCUtil.sleep(TimeUnit.SECONDS, 1);
            System.out.println(Thread.currentThread().getName() + "--" + i);
        }
    }
}

class MyThread02 implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            JUCUtil.sleep(TimeUnit.SECONDS, 1);
            System.out.println(Thread.currentThread().getName() + "--" + i);
        }
    }
}

class MyThread03{
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2, // 核心线程数
                4, // 最大线程数
                5000, TimeUnit.MILLISECONDS,  // 线程没有执行任务时最多存活时间
                new LinkedBlockingDeque<>()

        );

        for (int i = 0; i < 10; i++) {
            executor.execute(new ThreadTask());
        }
    }

    private static class ThreadTask implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
//            try {
//                TimeUnit.MILLISECONDS.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            JUCUtil.sleep(TimeUnit.MILLISECONDS, 1000);
        }
    }
}


