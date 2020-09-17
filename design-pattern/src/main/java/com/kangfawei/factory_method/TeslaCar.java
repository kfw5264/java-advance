package com.kangfawei.factory_method;

public class TeslaCar implements ICar {
    @Override
    public void energy() {
        System.out.println("特斯拉使用电......");
    }
}
