package com.kangfawei.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
    protected List<Observer> observers = new ArrayList<>();

    // 添加观察者
    public void add(Observer observer) {
        observers.add(observer);
    }
    // 移除观察者
    public void remove(Observer observer) {
        observers.remove(observer);
    }

    public abstract void notifyObserver();
}
