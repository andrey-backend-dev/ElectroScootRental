package com.example.electroscoot.utils.enums;

public enum OrderEnum {
    ASC("asc"), DESC("desc"), NULL("null");

    private String name;

    OrderEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
