package com.kangfawei.state;

public abstract class State {
    public String name;

    public abstract void handle(Context context);
}
