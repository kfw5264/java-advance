package com.kangfawei.facade;

import org.junit.Test;

public class App {

    @Test
    public void testMethod(){
        Facade facade = new Facade();
        facade.methodA();
        facade.methodB();
        facade.methodC();
    }
}
