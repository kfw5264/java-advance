package com.kangfawei.mediator;

import java.util.HashSet;
import java.util.Set;

public class ConcreteMedia implements Mediator {

    private Set<Colleague> colleagues = new HashSet<>();

    @Override
    public void register(Colleague colleague) {
        colleagues.add(colleague);
        colleague.setMediator(this);
    }

    @Override
    public void relay(Colleague col) {
        for (Colleague colleague : colleagues) {
            if(!col.equals(colleague)) {
                colleague.receive();
            }
        }
    }
}
