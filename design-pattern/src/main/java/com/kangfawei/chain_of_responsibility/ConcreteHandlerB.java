package com.kangfawei.chain_of_responsibility;

public class ConcreteHandlerB extends Handler {
    @Override
    public void handleRequest(String request) {
        if (request.equals("second")) {
            System.out.println("具体处理者B负责处理");
        } else if (getNext() != null) {
            getNext().handleRequest(request);
        } else {
            System.out.println("没有人处理");
        }
    }
}
