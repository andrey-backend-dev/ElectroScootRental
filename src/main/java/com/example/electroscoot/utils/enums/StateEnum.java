package com.example.electroscoot.utils.enums;

import lombok.val;

import java.util.HashMap;
import java.util.Map;

public enum StateEnum {
    OK("OK"), BROKEN("BROKEN");
    private final String enumName;

    private StateEnum(String enumName) {
        this.enumName = enumName;
    }

    @Override
    public String toString() {
        return enumName;
    }
}
