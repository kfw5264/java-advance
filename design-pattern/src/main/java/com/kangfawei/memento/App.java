package com.kangfawei.memento;

import org.junit.Test;

public class App {
    @Test
    public void testMemento() {
        Originator originator = new Originator();
        Cretaker cretaker = new Cretaker();
        originator.setState("first");
        System.out.println("初始状态："+originator.getState());
        cretaker.setMemento(originator.createMemento());
        originator.setState("second");
        System.out.println("新的状态:"+originator.getState());
        originator.restoreMemento(cretaker.getMemento());
        System.out.println("恢复到第一个状态:"+originator.getState());
    }
}
