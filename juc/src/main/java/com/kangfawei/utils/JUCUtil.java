package com.kangfawei.utils;

import java.util.concurrent.TimeUnit;

public class JUCUtil {

    public static void sleep(TimeUnit unit, Integer timeNum) {
        try {
            unit.sleep(timeNum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
