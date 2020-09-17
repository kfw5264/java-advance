package com.kangfawei.factory_method;

public class BenzCar implements ICar {

    @Override
    public void energy() {
        System.out.println("奔驰汽车用汽油.....");
    }
}
