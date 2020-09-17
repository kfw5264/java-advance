package com.kangfawei.observer;

public class ConcreteObserverB implements Observer {
    @Override
    public void response() {
        System.out.println("观察者B作出响应");
    }
}
