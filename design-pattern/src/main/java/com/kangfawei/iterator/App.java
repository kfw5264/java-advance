package com.kangfawei.iterator;

import org.junit.Test;

public class App {
    @Test
    public void testIterator() {
        Aggregate aggregate = new ConcreteAggregate();
        aggregate.add("a");
        aggregate.add("b");
        aggregate.add("c");
        aggregate.add("d");

        Iterator iterator = aggregate.getIterator();
        while (iterator.hasNext()){
            Object obj = iterator.next();
            System.out.println(obj.toString());
        }

        Object obj = iterator.first();
        System.out.println("first is "+obj.toString());
    }
}
