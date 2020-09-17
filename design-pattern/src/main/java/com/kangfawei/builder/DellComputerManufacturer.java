package com.kangfawei.builder;

public class DellComputerManufacturer extends ComputerManufacturer {
    @Override
    public void buildHardDisk() {
        this.computer.setHardDisk("西部数据");
    }

    @Override
    public void buildMemoryBank() {
        this.computer.setMemoryBank("美光");
    }

    @Override
    public void buildGraphicCard() {
        this.computer.setGraphicsCard("AMD X3600");
    }

    @Override
    public void buildMainboard() {
        this.computer.setMainboard("华硕Z490");
    }
}
