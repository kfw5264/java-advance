package com.kangfawei.singleton;

/**
 * 懒汉式02 多线程情况下依然可以保证单例，但是效率相对比较慢
 * @author kfw5264
 */
public class Demo03 {
    private static Demo03 instance;

    private Demo03(){}

    public synchronized static Demo03 getInstance(){
        if(instance == null){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            instance = new Demo03();
        }
        return instance;
    }
}
