package com.kangfawei.template_method;

public abstract class AbstractClass {

    public void templateMethod(){
        specificMethod();
        abstractMethodA();
        abstractMethodB();
    }
    // 具体方法
    public void specificMethod() {
        System.out.println("具体方法被调用");
    };
    // 抽象方法1
    public abstract void abstractMethodA();
    // 抽象方法2
    public abstract void abstractMethodB();


}
