package com.kangfawei.prototype;

public class MonkeySun implements Cloneable {

    public MonkeySun(){
//        System.out.println("创建了一个孙悟空实例");
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
