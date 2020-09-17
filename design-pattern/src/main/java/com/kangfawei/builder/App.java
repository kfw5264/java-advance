package com.kangfawei.builder;

import com.kangfawei.utils.ReadXmlUtil;

public class App {
    public static void main(String[] args) {
        ComputerManufacturer manufacturer ;
        try {
            manufacturer = (ComputerManufacturer) ReadXmlUtil.getClassName(" configuration/configuration3.xml");
            Shop shop = new Shop(manufacturer);
            Computer computer = shop.assemble();
            computer.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
