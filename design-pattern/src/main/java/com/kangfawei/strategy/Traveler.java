package com.kangfawei.strategy;

public class Traveler {

    private TravelStrategy travelStrategy;

    public Traveler(TravelStrategy travelStrategy) {
        this.travelStrategy = travelStrategy;
    }

    public void travel(){
        travelStrategy.travelMode();
    }
}
