package com.kangfawei.bridge;

public class RefinedAbstraction extends Abstraction {
    public RefinedAbstraction(Implementor implementor) {
        super(implementor);
    }

    @Override
    void doThings() {
        System.out.println("具体实现化角色");
        implementor.doSomething();
    }
}
