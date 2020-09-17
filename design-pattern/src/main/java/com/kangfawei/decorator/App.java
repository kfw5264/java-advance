package com.kangfawei.decorator;

import org.junit.Test;

public class App {
    @Test
    public void testDecorator(){
        Component component = new ConcreteComponent();
        Decorator decorator = new ConcreteDecorator(component);
        decorator.doSomething();
    }
}
