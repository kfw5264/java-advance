package com.kangfawei.factory_method;

public class TeslaFactory implements ICarFactory {
    @Override
    public ICar createCar() {
        return new TeslaCar();
    }
}
