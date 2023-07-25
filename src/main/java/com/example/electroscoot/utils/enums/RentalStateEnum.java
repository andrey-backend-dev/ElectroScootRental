package com.example.electroscoot.utils.enums;

public enum RentalStateEnum {
    OK("OK"), BAD("BAD"), CLOSED("CLOSED");
    private String name;

    RentalStateEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
