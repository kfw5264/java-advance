package com.kangfawei.mediator;

public interface Mediator {
    void register(Colleague colleague);
    void relay(Colleague colleague);
}
