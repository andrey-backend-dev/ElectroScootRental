package com.example.electroscoot.utils.mappers;

import com.example.electroscoot.utils.enums.OrderEnum;

import java.util.HashMap;
import java.util.Map;

public class OrderEnumMapper {
    private static final Map<String, OrderEnum> sortMap = new HashMap<>(Map.of(
            OrderEnum.ASC.getName(), OrderEnum.ASC,
            OrderEnum.DESC.getName(), OrderEnum.DESC
    ));

    public static OrderEnum getOrderingByName(String name) {
        return sortMap.getOrDefault(name.toLowerCase(), OrderEnum.NULL);
    }
}
