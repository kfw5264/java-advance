package com.kangfawei.facade;

public class Facade {
    private SubSystemA systemA;
    private SubSystemB systemB;
    private SubSystemC systemC;

    public Facade(){
        systemA = new SubSystemA();
        systemB = new SubSystemB();
        systemC = new SubSystemC();
    }

    public void methodA(){
        systemA.doSomethingA();
    }

    public void methodB(){
        systemB.doSomethingB();
    }

    public void methodC(){
        systemC.doSomethingC();
    }
}
