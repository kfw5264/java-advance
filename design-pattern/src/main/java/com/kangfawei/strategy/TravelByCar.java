package com.kangfawei.strategy;

public class TravelByCar implements TravelStrategy {
    @Override
    public void travelMode() {
        System.out.println("开车去旅行...");
    }
}
