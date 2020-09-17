package com.kangfawei.builder;

public class LenovoComputerManufacturer extends ComputerManufacturer {
    @Override
    public void buildHardDisk() {
        this.computer.setHardDisk("Samsung");
    }

    @Override
    public void buildMemoryBank() {
        this.computer.setMainboard("Kingston");
    }

    @Override
    public void buildGraphicCard() {
        this.computer.setGraphicsCard("NVIDIA RTX960");
    }

    @Override
    public void buildMainboard() {
        this.computer.setMainboard("微星B450");
    }
}
