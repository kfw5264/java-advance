package com.kangfawei.decorator;

public class ConcreteDecorator extends Decorator {
    public ConcreteDecorator(Component component) {
        super(component);
    }

    @Override
    public void doSomething() {
        super.doSomething();
        addFunction();
    }

    private void addFunction() {
        System.out.println("附加功能!");
    }
}
