package com.kangfawei.command;

import org.junit.Test;

public class App {
    @Test
    public void testCommand() {
        Command command = new ConcreteCommand();
        Invoker invoker = new Invoker(command);
        invoker.call();
    }
}
