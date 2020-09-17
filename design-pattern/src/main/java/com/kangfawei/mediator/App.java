package com.kangfawei.mediator;

import org.junit.Test;

public class App {

    @Test
    public void testMediator() {
        Mediator mediator = new ConcreteMedia();
        Colleague colleagueA = new ConcreteColleagueA();
        Colleague colleagueB = new ConcreteColleagueB();

        mediator.register(colleagueA);
        mediator.register(colleagueB);

        colleagueA.send();
        colleagueB.send();

    }
}
