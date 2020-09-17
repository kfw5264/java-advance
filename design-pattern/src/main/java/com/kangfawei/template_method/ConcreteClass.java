package com.kangfawei.template_method;

public class ConcreteClass extends AbstractClass {
    @Override
    public void abstractMethodA() {
        System.out.println("抽象类的抽象方法A");
    }

    @Override
    public void abstractMethodB() {
        System.out.println("抽象类的抽象方法B");
    }
}
