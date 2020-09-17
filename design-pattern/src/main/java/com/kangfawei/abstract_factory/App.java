package com.kangfawei.abstract_factory;

import com.kangfawei.utils.ReadXmlUtil;

public class App {
    public static void main(String[] args) {
        Person p;
        Food food;
        Weapon weapon;

        World world;
        try {
            world =  (World) ReadXmlUtil.getClassName("configuration/configuration2.xml");
            p = world.createPerson();
            food = world.createFood();
            weapon = world.createWeapon();

            p.skill(food, weapon);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
