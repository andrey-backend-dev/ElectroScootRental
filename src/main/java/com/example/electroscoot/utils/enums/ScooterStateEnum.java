package com.example.electroscoot.utils.enums;

public enum ScooterStateEnum {
    NULL("null"), OK("ok"), BROKEN("broken"), RENTED("rented");
    private final String enumName;

    ScooterStateEnum(String enumName) {
        this.enumName = enumName;
    }

    public String getName() {
        return this.enumName;
    }

    @Override
    public String toString() {
        return enumName;
    }
}
