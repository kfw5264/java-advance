package com.kangfawei.flyweight;

import java.util.HashMap;
import java.util.Map;

public class FlyweightFactory {

    private Map<String, Flyweight> flyweightMap = new HashMap<>();

    public Flyweight getFlyweight(String key) {
        Flyweight flyweight = flyweightMap.get(key);
        if (flyweight != null) {
            System.out.println("具体享元对象"+key+"已经存在");
        }else{
            flyweight = new ConcreteFlyweight(key);
            flyweightMap.put(key, flyweight);
        }

        return flyweight;
    }
}
