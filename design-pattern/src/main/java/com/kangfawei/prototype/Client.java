package com.kangfawei.prototype;

public class Client {

    public static void main(String[] args) throws CloneNotSupportedException {
       MonkeySun  monkeySun = new MonkeySun();
       MonkeySun monkeySunCopy = (MonkeySun) monkeySun.clone();
        System.out.println(monkeySun == monkeySunCopy);
    }
}
