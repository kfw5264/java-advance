package com.kangfawei.singleton;

/**
 * 利用内部类创建一个单例  类加载的时候内部类中的静态域不会被加载  这样可以实现懒加载
 * @author kfw5264
 */
public class Demo06 {
    private Demo06(){}

    private static class Demo06Holder{
        private final static Demo06 INSTANCE = new Demo06();
    }

    public static Demo06 getInstance(){
        return Demo06Holder.INSTANCE;
    }
}
