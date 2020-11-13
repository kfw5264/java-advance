package com.kangfawei.item02;

import java.util.HashMap;
import java.util.Map;

/**
 * 并发情况下的HashMap出现的一些问题
 */
public class HashMapMultiThread {
    static Map<String, String> map = new HashMap<>();

    private static class AddDateToMap implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                map.put(Thread.currentThread().getName() + i, i + "");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AddDateToMap r1 = new AddDateToMap();
        AddDateToMap r2 = new AddDateToMap();

        Thread t1 = new Thread(r1, "t1");
        Thread t2 = new Thread(r2, "t2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(map.size());
    }
}
