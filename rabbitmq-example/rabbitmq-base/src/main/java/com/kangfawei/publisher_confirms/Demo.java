package com.kangfawei.publisher_confirms;

/**
 * static块跟main方法哪个先执行
 * @author kangfaiwei
 */
public class Demo {

    static {
        System.out.println("static");
    }

    public static void main(String[] args) {
        System.out.println("main");
    }
}
