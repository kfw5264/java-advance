package com.kangfawei.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxy {
    // 定义一个目标对象
    private Object target;

    public DynamicProxy(Object target){
        this.target = target;
    }

    // 传统方法
    public Object getProxyInstance(){
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        preRequest();
                        Object returnValue = method.invoke(target, args);
                        afterRequest();

                        return  returnValue;
                    }
                }
        );
    }

    // lambda表达是
    public Object getProxyInstanceLambda(){
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    preRequest();
                    Object returnValue = method.invoke(target, args);
                    afterRequest();

                    return  returnValue;
                }
        );
    }

    private void preRequest(){
        System.out.println("动态代理访问真实主题方法之前的预处理.......");
    }
    private void afterRequest(){
        System.out.println("动态代理访问真实主题之后的后续处理......");
    }
}
