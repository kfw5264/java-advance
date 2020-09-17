package com.kangfawei.factory_method.inner;

public class BMWCarFactory implements CarFactory {
    @Override
    public Car createCar() {
        return new Car() {
            @Override
            public String brand() {
                return "宝马";
            }

            @Override
            public void run() {
                System.out.println(this.brand()+"在马路上奔驰。。。");
            }
        };
    }
}
