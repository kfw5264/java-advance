package com.kangfawei.abstract_factory;

public class MagicWorld implements World {
    @Override
    public Person createPerson() {
        return new WitchPerson();
    }

    @Override
    public Food createFood() {
        return new MushroomFood();
    }

    @Override
    public Weapon createWeapon() {
        return new MagicWandWeapon();
    }
}
