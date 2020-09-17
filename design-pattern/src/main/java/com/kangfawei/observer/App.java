package com.kangfawei.observer;

import org.junit.Test;

public class App {
    @Test
    public void testObserver() {
        Subject subject = new ConcreteSubject();
        Observer observerA = new ConcreteObserverA();
        subject.add(observerA);
        Observer observerB = new ConcreteObserverB();
        subject.add(observerB);

        subject.notifyObserver();
    }
}
