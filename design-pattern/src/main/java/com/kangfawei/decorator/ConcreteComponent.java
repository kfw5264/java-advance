package com.kangfawei.decorator;

public class ConcreteComponent implements Component {
    @Override
    public void doSomething() {
        System.out.println("具体构件的方法");
    }
}
