package com.kangfawei.mediator;

public class ConcreteColleagueB extends Colleague {
    @Override
    public void receive() {
        System.out.println("同事类B接收请求");
    }

    @Override
    public void send() {
        System.out.println("同事类B发送请求");
        mediator.relay(this);
    }
}
