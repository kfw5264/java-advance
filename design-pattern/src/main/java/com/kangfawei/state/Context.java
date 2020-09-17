package com.kangfawei.state;

public class Context {
    private State state;

    public Context(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        System.out.println("当前状态为"+state.name);
    }

    public void request() {
        state.handle(this); //对请求做处理并指向下一个状态
    }
}
