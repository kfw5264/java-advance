package com.kangfawei.proxy;

public class StaticProxy implements Subject {

    private Subject subject;

    public StaticProxy(Subject subject){
        this.subject = subject;
    }

    @Override
    public void request() {
        preRequest();
        subject.request();
        afterRequest();
    }

    public void preRequest(){
        System.out.println("访问真实主题方法之前的预处理.......");
    }
    public void afterRequest(){
        System.out.println("访问真实主题之后的后续处理......");
    }
}
