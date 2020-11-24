package com.kangfawei.work_queue;

import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            NewTask.main(new String[]{"这是第" + i + "条消息..."});

            TimeUnit.MILLISECONDS.sleep(500);
        }
    }
}
