package com.kangfawei.item02;

import com.kangfawei.utils.JUCUtil;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierForLOL {
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(10);
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                JUCUtil.sleep(TimeUnit.MILLISECONDS, random.nextInt(5000));
                System.out.println("红方选手————" + Thread.currentThread().getName() + "加载完成");

                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("红方选手————" + Thread.currentThread().getName() + "欢迎来到英雄联盟，敌军还有30s进入战场。");
                }
            }, "t" + i).start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                JUCUtil.sleep(TimeUnit.MILLISECONDS, random.nextInt(5000));
                System.out.println("蓝方选手————" + Thread.currentThread().getName() + "加载完成");

                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("蓝方选手————" + Thread.currentThread().getName() + "欢迎来到英雄联盟，敌军还有30s进入战场。");
                }
            }, "t" + (i +5)).start();
        }
    }
}
