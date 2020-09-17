package com.kangfawei.singleton;

/**
 * 利用枚举类创建单例对象
 * @author kfw5264
 */
public enum Demo07 {
    INSTANCE;

    public void print(){
        System.out.println(INSTANCE.hashCode()+"------");
    }
}
