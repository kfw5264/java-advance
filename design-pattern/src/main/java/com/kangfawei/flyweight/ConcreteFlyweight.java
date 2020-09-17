package com.kangfawei.flyweight;

public class ConcreteFlyweight implements Flyweight {

    private String key;

    public ConcreteFlyweight(String key) {
        this.key = key;
    }

    @Override
    public void doSomething(UnsharableFlyweight unsharableFlyweight) {
        System.out.println("具体享元");
        System.out.println("非享元信息是："+unsharableFlyweight.getInfo());
    }
}
