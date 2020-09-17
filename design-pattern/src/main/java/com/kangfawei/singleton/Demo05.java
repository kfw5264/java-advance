package com.kangfawei.singleton;

/**
 * 懒汉式4  利用双重检查增加效率，较为完美的方法
 * @author kfw5264
 */
public class Demo05 {
    // volatile避免指令重排
    private volatile static Demo05 instance;

    private Demo05(){}

    public static Demo05 getInstance(){
        if(instance == null){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (Demo05.class){
                if(instance == null){
                    instance = new Demo05();
                }
            }
        }
        return instance;
    }
}
