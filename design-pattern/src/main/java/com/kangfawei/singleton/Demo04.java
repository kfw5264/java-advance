package com.kangfawei.singleton;

/**
 * 饿汉式3  锁效加锁范围，但是多线程情况下依然会产生多个实例
 * @author kfw5264
 */
public class Demo04 {
    private volatile static Demo04 instance;

    private Demo04(){}

    public static Demo04 getInstance(){
        if(instance == null){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (Demo04.class){
                instance = new Demo04();
            }
        }
        return instance;
    }
}
