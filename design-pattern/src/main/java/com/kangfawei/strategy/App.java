package com.kangfawei.strategy;

import org.junit.Test;

public class App {
    @Test
    public void testTraveler(){
//        Traveler traveler = new Traveler(new TravelByCar());
//        Traveler traveler = new Traveler(new TravelByTrain());
        Traveler traveler = new Traveler(new TravelByPlain());
        traveler.travel();
    }
}
