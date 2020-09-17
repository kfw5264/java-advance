package com.kangfawei.strategy;

public class TravelByTrain implements TravelStrategy {
    @Override
    public void travelMode() {
        System.out.println("坐火车去旅行。。。");
    }
}
