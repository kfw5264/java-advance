package com.kangfawei.bridge;

public abstract class Abstraction {

    // 子类需要继承，所以是protected
    protected Implementor implementor;

    public Abstraction(Implementor implementor){
        this.implementor = implementor;
    }

    abstract void doThings();

}
