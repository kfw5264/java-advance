package com.kangfawei.builder;

public abstract class ComputerManufacturer {

    protected Computer computer = new Computer();

    public abstract void buildHardDisk();
    public abstract void buildMemoryBank();
    public abstract void buildGraphicCard();
    public abstract void buildMainboard();

    public Computer getResult(){
        return computer;
    }
}
