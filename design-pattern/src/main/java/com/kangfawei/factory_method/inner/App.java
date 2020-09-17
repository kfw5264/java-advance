package com.kangfawei.factory_method.inner;

import com.kangfawei.utils.ReadXmlUtil;
import org.junit.Test;

public class App {
    @Test
    public void testFactory() {
        Car car;
        CarFactory carFactory;
        try {
            carFactory = (CarFactory) ReadXmlUtil.getClassName("configuration/configuration4.xml");
            car = carFactory.createCar();
            car.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
