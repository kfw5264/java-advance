package com.kangfawei.template_method;

import org.junit.Test;

public class App {

    @Test
    public void testTemplateMethod() {
        AbstractClass abstractClass = new ConcreteClass();
        abstractClass.templateMethod();
    }
}
