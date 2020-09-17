package com.kangfawei.observer;

public class ConcreteObserverA implements Observer {
    @Override
    public void response() {
        System.out.println("观察者A作出响应");
    }
}
