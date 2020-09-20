package com.kangfawei.item01;

/**
 * Thread.sleep(Long millis)
 * Thread.yield()
 * Thread.join()
 * @author kangfawei
 */
public class ThreadDemo02 {
    public static void main(String[] args) {
        Thread thread = new Thread(new MyThread(), "MyThread");
        thread.start();

        for (int i = 0; i < 10; i++) {
            System.out.println("mian--" + i);
            try {
                Thread.sleep(500);
                if(i == 2) {
                    // 暂停该线程
                    Thread.yield();
                }
                if(i == 5) {
                    // 等待thread线程执行完成再执行
                    thread.join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class MyThread implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "--" + i);
            }
        }
    }
}



