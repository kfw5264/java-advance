package com.kangfawei.state;

import org.junit.Test;

public class App {

    @Test
    public void testState(){
        Context context = new Context(new ConcreteStateA());
        context.request();
    }
}
