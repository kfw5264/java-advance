package com.kangfawei.state;

public class ConcreteStateA extends State {

    public ConcreteStateA() {
        this.name = "A";
    }

    @Override
    public void handle(Context context) {
        System.out.println(this.name);
        context.setState(new ConcreteStateB());
    }
}
