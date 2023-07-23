package com.example.electroscoot.utils.enums;

public enum SortMethod {
    NULL("null"), ADDRESS("address");
    private String name;

    SortMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
