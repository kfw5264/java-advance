package com.kangfawei.bridge;

import org.junit.Test;

public class App {
    @Test
    public static void main(String[] args){
        Implementor implementor = new ConcreteImplementor();
        Abstraction abstraction = new RefinedAbstraction(implementor);
        abstraction.doThings();
    }
}
