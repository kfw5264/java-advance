package com.kangfawei.singleton;

/**
 * 懒汉式1：多线程情况下会产生多个单例
 * @author kfw52564
 */
public class Demo02 {
    private static Demo02 instance;

    private Demo02(){}

    public static Demo02 getInstance(){
        if (instance == null){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            instance = new Demo02();
        }
        return instance;
    }
}
