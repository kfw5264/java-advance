package com.kangfawei.abstract_factory;

public interface World {
    Person createPerson();

    Food createFood();

    Weapon createWeapon();
}
