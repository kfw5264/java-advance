package com.kangfawei.strategy;

public class TravelByPlain implements TravelStrategy {
    @Override
    public void travelMode() {
        System.out.println("坐着飞机去旅行...");
    }
}
