package com.kangfawei.item02;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class PhaserDemo {

    public static void main(String[] args) {
        Phaser phaser = new MyPhaser(7);
//        phaser.bulkRegister(7);
        for (int i = 0; i < 5; i++) {
            new Thread(new Person("p" + i, phaser)).start();
        }
        new Thread(new Person("新郎", phaser)).start();
        new Thread(new Person("新娘", phaser)).start();
    }

    static void milliSleep(int milli) {
        try {
            TimeUnit.MILLISECONDS.sleep(milli);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class MyPhaser extends Phaser {
        protected MyPhaser(int parties) {
            super(parties);
        }

        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            switch (phase) {
                case 0:
                    System.out.println("所有人都到齐了" + registeredParties);
                    System.out.println("--------------------------------------------");
                    return false;
                case 1:
                    System.out.println("所有人都吃完了" + registeredParties);
                    System.out.println("--------------------------------------------");
                    return false;
                case 2:
                    System.out.println("所有人都离开了" + registeredParties);
                    System.out.println("--------------------------------------------");
                    return false;
                case 3:
                    System.out.println("婚礼结束， 新郎新娘入洞房！" + registeredParties);
                    return false;
                default:
                    throw new IllegalStateException("Unexpected value: " + phase);
            }
        }
    }

    static class Person implements Runnable {
        private String name;
        private Phaser phaser;

        public Person(String name, Phaser phaser) {
            this.name = name;
            this.phaser = phaser;
        }

        private void arrive() {
            milliSleep(100);
            System.out.println(name + "到达现场");
            phaser.arriveAndAwaitAdvance();
        }

        private void eat() {
            milliSleep(100);
            System.out.println(name + "吃完了");
            phaser.arriveAndAwaitAdvance();
        }

        private void leave() {
            milliSleep(100);
            System.out.println(name + "酒足饭饱，离开了婚礼现场！");
            phaser.arriveAndAwaitAdvance();
        }

        private void hug() {
            milliSleep(100);
            if(name.equals("新郎") || name.equals("新娘")) {
                System.out.println(name + "入洞房");
            } else {
                phaser.arriveAndDeregister();
            }
        }

        @Override
        public void run() {
            arrive();
            eat();
            leave();
            hug();
        }
    }



}
