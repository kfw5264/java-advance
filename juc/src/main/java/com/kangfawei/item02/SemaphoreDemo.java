package com.kangfawei.item02;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore示例  抢车位
 * @author kangfawei
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);  // 只有三个车位

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "占用一个车位");

                    TimeUnit.SECONDS.sleep(2);

                    System.out.println(Thread.currentThread().getName() + "离开车位，剩余车位+1");
                    semaphore.release();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "t"+i).start();
        }
    }
}
