package com.example.electroscoot.utils.mappers;

import com.example.electroscoot.utils.enums.SortMethod;

import java.util.HashMap;
import java.util.Map;

public class SortEnumMapper {
    private static final Map<String, SortMethod> sortMap = new HashMap<>(Map.of(
            SortMethod.ADDRESS.getName(), SortMethod.ADDRESS,
            SortMethod.STATE.getName(), SortMethod.STATE
            ));


    public static SortMethod getSortByName(String name) {
        return sortMap.getOrDefault(name.toLowerCase(), SortMethod.NULL);
    }
}
