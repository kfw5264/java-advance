package com.kangfawei.item01;

/**
 * volatile禁止指令重排序
 * DCL单例
 * @author kangfawei
 */
public class ThreadDemo05 {
    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Singleton.getInstance().hashCode());
                }
            });
            thread.start();
        }
    }
}

class Singleton {

    // volatile禁止重排序
    private static volatile Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if(instance == null) {
            synchronized (Singleton.class) {
                if(instance == null) {
                    instance = new Singleton();
                }
            }
        }

        return instance;
    }
}
