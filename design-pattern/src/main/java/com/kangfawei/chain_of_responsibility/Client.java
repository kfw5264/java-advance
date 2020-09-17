package com.kangfawei.chain_of_responsibility;

public class Client {
    public void invokeChain() {
        Handler handlerA = new ConcreteHandlerA();
        Handler handlerB = new ConcreteHandlerB();

        handlerA.setNext(handlerB);

        handlerA.handleRequest("second");
    }
}
