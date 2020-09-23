package com.kangfawei.item02;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * synchronized、AtomicInteger、LongAdder三种方式递增测试
 * @author kangfawei
 */
public class IncreaseDemo {

    public static void main(String[] args) throws InterruptedException {
        ThreeMethodIncreaseDemo demo = new ThreeMethodIncreaseDemo();
        List<Thread> syncThreads = new ArrayList<>();
        List<Thread> atomicThreads = new ArrayList<>();
        List<Thread> adderThreads = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            // lambda表达式写法，等同于：
            /*  syncThreads.add(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        demo.syncCount
                    }
                })) */
            syncThreads.add(new Thread(demo :: increaseWithSync));
            atomicThreads.add(new Thread(demo :: increaseWithAtomic));
            adderThreads.add(new Thread(demo :: increaseWithLongAdder));
        }

        long syncTime = testTime(syncThreads);
        long atomicTime = testTime(atomicThreads);
        long adderTime = testTime(adderThreads);

        System.out.println("sync:" + syncTime + "-" + demo.syncCount);
        System.out.println("atomic:" + atomicTime + "-" + demo.atomicCount);
        System.out.println("adder:" + adderTime + "-" + demo.adderCount);
    }

    private static Long testTime(List<Thread> threads) throws InterruptedException {
        long start = System.currentTimeMillis();
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        return System.currentTimeMillis() - start;
    }

}

class ThreeMethodIncreaseDemo {
    Integer syncCount = 0;
    AtomicInteger atomicCount = new AtomicInteger(0);
    LongAdder adderCount = new LongAdder();

    public void increaseWithSync() {
        for (int i = 0; i < 10000; i++) {
            synchronized (this) {
                syncCount++;
            }
        }
    }

    public void increaseWithAtomic() {
        for (int i = 0; i < 10000; i++) {
            atomicCount.incrementAndGet();
        }
    }

    public void increaseWithLongAdder() {
        for (int i = 0; i < 10000; i++) {
            adderCount.increment();
        }
    }

}
