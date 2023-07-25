package com.example.electroscoot.utils.enums;

public enum ScooterStateEnum {
    OK("OK"), BROKEN("BROKEN"), RENTED("RENTED");
    private final String enumName;

    ScooterStateEnum(String enumName) {
        this.enumName = enumName;
    }

    @Override
    public String toString() {
        return enumName;
    }
}
