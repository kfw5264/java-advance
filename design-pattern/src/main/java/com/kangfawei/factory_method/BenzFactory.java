package com.kangfawei.factory_method;

public class BenzFactory implements ICarFactory {
    @Override
    public ICar createCar() {
        return new BenzCar();
    }
}
