package com.kangfawei;

import com.kangfawei.singleton.*;
import org.junit.Test;

public class singletonTest {
    @Test
    public void testDemo01(){
        for (int i = 0; i < 10000; i++) {
            new Thread(()->{
                System.out.println(Demo01.getInstance().hashCode());
            }).start();
        }
    }

    @Test
    public void testDemo02(){
        for (int i = 0; i < 10000; i++) {
            new Thread(()->{
                System.out.println(Demo02.getInstance().hashCode());
            }).start();
        }
    }

    @Test
    public void testDemo03(){
        for (int i = 0; i < 10000; i++) {
            new Thread(()->{
                System.out.println(Demo03.getInstance().hashCode());
            }).start();
        }
    }

    @Test
    public void testDemo04(){
        for (int i = 0; i < 10000; i++) {
            new Thread(()->{
                System.out.println(Demo04.getInstance().hashCode());
            }).start();
        }
    }

    @Test
    public void testDemo05(){
        for (int i = 0; i < 10000; i++) {
            new Thread(()->{
                System.out.println(Demo05.getInstance().hashCode());
            }).start();
        }
    }

    @Test
    public void testDemo06(){
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                System.out.println(Demo06.getInstance().hashCode());
            }).start();
        }
    }

    @Test
    public void testDemo07(){
        for (int i = 0; i < 10000; i++) {
            new Thread(()->{
                System.out.println(Demo07.INSTANCE.hashCode());
            }).start();
        }
    }
}
