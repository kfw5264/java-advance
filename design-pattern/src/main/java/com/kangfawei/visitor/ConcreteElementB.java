package com.kangfawei.visitor;

public class ConcreteElementB implements Element {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
    public String operationA() {
        return "具体元素B的操作";
    }
}
