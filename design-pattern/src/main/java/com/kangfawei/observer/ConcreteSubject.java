package com.kangfawei.observer;

public class ConcreteSubject extends Subject {
    @Override
    public void notifyObserver() {
        System.out.println("目标发生改变，调用观察者");
        for (Observer observer : observers) {
            observer.response();
        }
    }
}
