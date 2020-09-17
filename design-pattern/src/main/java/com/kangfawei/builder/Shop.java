package com.kangfawei.builder;

public class Shop {
    private ComputerManufacturer manufacturer;

    public Shop(ComputerManufacturer manufacturer){
        this.manufacturer = manufacturer;
    }

    public Computer assemble(){
        manufacturer.buildGraphicCard();
        manufacturer.buildHardDisk();
        manufacturer.buildMainboard();
        manufacturer.buildMemoryBank();

        return manufacturer.getResult();
    }
}
