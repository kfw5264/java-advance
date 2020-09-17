package com.kangfawei.abstract_factory;

public class ModernWorld implements World {
    @Override
    public Person createPerson() {
        return new SoldlerPerson();
    }

    @Override
    public Food createFood() {
        return new HamburgerFood();
    }

    @Override
    public Weapon createWeapon() {
        return new GunWeapon();
    }
}
