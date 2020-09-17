package com.kangfawei.factory_method;

import com.kangfawei.utils.ReadXmlUtil;

public class App {
    public static void main(String[] args) {
        ICar car ;
        ICarFactory factory;
        try {
            factory = (ICarFactory) ReadXmlUtil.getClassName("configuration/configuration.xml");
            // E:\workspace\ide\design-pattern\src\main\resources\configuration
            car = factory.createCar();
            car.energy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
