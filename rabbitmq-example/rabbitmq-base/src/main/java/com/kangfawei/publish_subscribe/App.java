package com.kangfawei.publish_subscribe;

/**
 * @author kangfawei
 */
public class App {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            LogDispatcher.main(new String[]{"info:这是第" + (i+10) + "条日志"});
        }
    }
}
