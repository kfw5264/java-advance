package com.kangfawei.bridge;

public class ConcreteImplementor implements Implementor {
    @Override
    public void doSomething() {
        System.out.println("扩展抽象化角色");
    }
}
