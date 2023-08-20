package com.example.electroscoot.utils.enums;

public enum UserStatus {
    OK("OK"), BANNED("BANNED");

    private final String name;
    UserStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
