package com.kangfawei.item02;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockConditionDemo {

    public static void main(String[] args) {

        ProduceAndConsumer produceAndConsumer = new ProduceAndConsumer();

        for (int i = 0; i < 2; i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    produceAndConsumer.put("产品" + finalI);
                    System.out.println(Thread.currentThread().getName() + "生产了产品" + finalI);
                }
            }, "P" + i).start();

        }

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String s = (String)produceAndConsumer.get();
                    System.out.println(Thread.currentThread().getName() + "消费了" + s);
                }
            }, "C" + i).start();
        }
    }
}

class ProduceAndConsumer {
    final Lock lock  = new ReentrantLock();
    final Condition putCondition = lock.newCondition();
    final Condition getCondition = lock.newCondition();
    final Object[] objs = new Object[10];
    int putIndex, getIndex, count;

    public void put(Object obj) {
        lock.lock();
        try {
            // 如果count跟数组大小相同，说明已经存满了，此时停止放入线程
            while (count == objs.length) {
                putCondition.await();
            }
            objs[putIndex] = obj;
            // 如果存到最后一个那就从第一个开始
            if(++putIndex == objs.length) {
                putIndex = 0;
            }
            count ++;
            // 存入一个之后说明库里有数据了，然后唤醒消费者线程
            getCondition.signal();


        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public Object get() {
        Object obj = null;
        lock.lock();
        // 如果count为零，说明数组中已经取完了，此时暂停取线程
        try {
            while(count == 0) {
                getCondition.await();
            }
            obj = objs[getIndex];
            if(++getIndex == objs.length) {
                getIndex = 0;
            }
            count--;

            putCondition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return obj;
    }


}