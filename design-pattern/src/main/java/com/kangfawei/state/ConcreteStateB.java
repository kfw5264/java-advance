package com.kangfawei.state;

public class ConcreteStateB extends State {

    public ConcreteStateB() {
        this.name = "B";
    }

    @Override
    public void handle(Context context) {
        System.out.println("");
        context.setState(new ConcreteStateA());
    }
}
