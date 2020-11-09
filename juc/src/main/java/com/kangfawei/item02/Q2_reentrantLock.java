package com.kangfawei.item02;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 写一个固定容量的同步容器，拥有`put()`和`get()`方法，以及`getCount()`方法，能够支持两个生产者线程以及10个消费者线程的阻塞使用。
 */
public class Q2_reentrantLock {
    static final Object[] objs = new Object[10];
    static final int ARRAY_SIZE = 10;

    public static void main(String[] args) {
        ProductionContainer container = new ProductionContainer();


        // 两个生产者线程
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                while (true) {
                    container.put(Thread.currentThread().getName());
                    System.out.println(Thread.currentThread().getName() + "生产了产品");
                }

            }, "生产者t" + i).start();
        }

        // 十个消费者线程
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    System.out.println(Thread.currentThread().getName() + "消费了" + (String) container.get());
                }
            }, "消费者t" + i).start();
        }
    }


    private static class ProductionContainer {
        ReentrantLock lock = new ReentrantLock();
        Condition producer = lock.newCondition();
        Condition consumer = lock.newCondition();

        final int MAX_SIZE = 10;  // 容器最大容量
        final Object[] arr = new Object[MAX_SIZE];  // 具体容器
        int size = 0; // 容器中的产品数量
        int putIndex, getIndex; // 生产者下标，消费者下标

        // 往容器中放入产品
        public void put(Object obj) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            // 如果容器中产品数量已经是最大容量，生产着线程进入等待状态
            while (size == MAX_SIZE) {  // 此处用while而不是if，防止容器满的时候继续添加
                try {
                    producer.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // 容器中加一个元素
            arr[putIndex] = obj;
            putIndex++;  // 生产者下标+1
            size++;
            if (putIndex == MAX_SIZE) {  // 如果生产者下标大于最大容量的时候，生产者下标变为0
                putIndex = 0;
            }

            consumer.signal(); // 放入之后释放消费者的锁，让消费者线程开始消费
            lock.unlock();
        }

        // 从容器中取出产品
        public Object get() {
            Object obj = null;
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 如果容器中的产品数量为0时，消费者线程等待
            lock.lock();
            while (size == 0) {
                try {
                    consumer.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            obj = arr[getIndex];
            getIndex++;
            size--;
            if (getIndex == MAX_SIZE) {
                getIndex = 0;
            }

            producer.signal();

            return obj;

        }
    }

}
