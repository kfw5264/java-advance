package com.kangfawei.item02;

public class Q3_waitAndNotify {
    public static void main(String[] args) {

        new Thread(() -> {
            synchronized (Q3_waitAndNotify.class) {
                for (int i = 0; i < 26; i++) {
                    System.out.print(i+1 + " ");

                    Q3_waitAndNotify.class.notifyAll();
                    try {
                        Q3_waitAndNotify.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (Q3_waitAndNotify.class) {
                for (int i = 65; i < 91; i++) {

                    System.out.println((char)i);
                    Q3_waitAndNotify.class.notifyAll();
                    if (i != 90) { // 保证到最后程序结束
                        try {
                            Q3_waitAndNotify.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
}
