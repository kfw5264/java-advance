package com.kangfawei.proxy;

import org.junit.Test;

public class App {
    @Test
    public void testStaticProxy(){
        Subject subject = new RealSubject();
        StaticProxy proxy = new StaticProxy(subject);
        proxy.request();
    }

    @Test
    public void testDynamicProxy(){
       Subject subject = (Subject) new DynamicProxy(new RealSubject()).getProxyInstanceLambda();
       subject.request();
    }


}
