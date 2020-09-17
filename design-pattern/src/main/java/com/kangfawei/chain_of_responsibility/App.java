package com.kangfawei.chain_of_responsibility;

import org.junit.Test;

public class App {
    @Test
    public void testChainOfResponsibility() {
        Client client = new Client();
        client.invokeChain();
    }
}
