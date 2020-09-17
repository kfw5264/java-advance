package com.kangfawei;

import com.kangfawei.strategy.TravelByPlain;
import com.kangfawei.strategy.Traveler;
import org.junit.Test;

public class StrategyTest {

    @Test
    public void testTraveler(){
//        Traveler traveler = new Traveler(new TravelByCar());
//        Traveler traveler = new Traveler(new TravelByTrain());
        Traveler traveler = new Traveler(new TravelByPlain());
        traveler.travel();
    }

}
