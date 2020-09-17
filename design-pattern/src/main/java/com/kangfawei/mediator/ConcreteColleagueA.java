package com.kangfawei.mediator;

public class ConcreteColleagueA extends Colleague {
    @Override
    public void receive() {
        System.out.println("同事类A接收到请求");
    }

    @Override
    public void send() {
        System.out.println("同事类A发送请求");
        mediator.relay(this);
    }
}
