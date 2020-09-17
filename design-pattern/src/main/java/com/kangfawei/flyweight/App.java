package com.kangfawei.flyweight;

import org.junit.Test;

public class App {

    @Test
    public void testFlyweight(){
        FlyweightFactory factory = new FlyweightFactory();
        Flyweight f1 = factory.getFlyweight("a");
        Flyweight f2 = factory.getFlyweight("a");
        Flyweight f3 = factory.getFlyweight("a");
        Flyweight f4 = factory.getFlyweight("b");
        Flyweight f5 = factory.getFlyweight("c");

        f1.doSomething(new UnsharableFlyweight("第一次调用a"));
        f2.doSomething(new UnsharableFlyweight("第二次调用a"));
        f3.doSomething(new UnsharableFlyweight("第三次调用a"));
        f4.doSomething(new UnsharableFlyweight("第一次调用b"));
        f5.doSomething(new UnsharableFlyweight("第一次调用c"));
    }
}
