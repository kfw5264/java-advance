package com.kangfawei.item02;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现一个容器，提供两个方法`add()`和`size()`，写两个线程，线程以添加10个元素到容器中
 * 线程2实现监控元素的个数，当格式到5个时，线程2给出提示并结束。
 */
public class Q1_waitAndNotify {
    static final List<Integer> list = new ArrayList<>();
    static Thread t1 = null, t2 = null;
    static final Object lock = new Object();
    public static void main(String[] args) {


        t1 = new Thread(() -> {
            synchronized (lock) {
                for (int i = 0; i < 10; i++) {
                    if(i == 5) {
                        lock.notify();

                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }


                    list.add(i);
                    System.out.println("t1添加了第" + (i+1) + "个元素！");
                }
            }
        });
        t2 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("t2启动");
                if(list.size() != 5) {

                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("------已经往集合中添加了五个元素了！-----");
                System.out.println("t2结束");
                lock.notify();
            }
        });

        t2.start();
        t1.start();
    }
}
