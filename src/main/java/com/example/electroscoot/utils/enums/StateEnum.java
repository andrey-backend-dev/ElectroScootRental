package com.example.electroscoot.utils.enums;

public enum StateEnum {
    OK("OK"), BROKEN("BROKEN"), RENTED("RENTED");
    private final String enumName;

    StateEnum(String enumName) {
        this.enumName = enumName;
    }

    @Override
    public String toString() {
        return enumName;
    }
}
