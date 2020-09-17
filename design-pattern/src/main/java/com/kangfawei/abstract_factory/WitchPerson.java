package com.kangfawei.abstract_factory;

public class WitchPerson implements Person {
    @Override
    public void skill(Food food, Weapon weapon) {
        weapon.shot();
        food.name();
    }
}
