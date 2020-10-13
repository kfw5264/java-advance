package com.kangfawei.item01;

import com.kangfawei.utils.JUCUtil;

import java.util.concurrent.TimeUnit;

/**
 * volatile保证线程可见性
 * @author kangfawei
 */
public class ThreadDemo04 {

    public static void main(String[] args) {
        VolatileTest test = new VolatileTest();
        Thread thread = new Thread(test, "T1");
        thread.start();
//        try {
//            TimeUnit.SECONDS.sleep(2);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        JUCUtil.sleep(TimeUnit.SECONDS, 2);

        test.flag = false;
    }
}

class VolatileTest implements Runnable{

    boolean flag = true;

    @Override
    public void run() {
        while(flag) {
            // 通过更改变量flag让线程停止
            // 如果不加volatile线程会一直向下运行
        }
    }
}
