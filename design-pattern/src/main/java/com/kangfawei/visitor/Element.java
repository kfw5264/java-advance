package com.kangfawei.visitor;

public interface Element {
    void accept(Visitor visitor);
}
