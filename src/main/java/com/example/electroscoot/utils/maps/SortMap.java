package com.example.electroscoot.utils.maps;

import com.example.electroscoot.utils.enums.SortMethod;

import java.util.HashMap;
import java.util.Map;

public class SortMap {
    private static Map<String, SortMethod> sortMap = new HashMap<>(Map.of(
            SortMethod.NULL.getName(), SortMethod.NULL,
            SortMethod.ADDRESS.getName(), SortMethod.ADDRESS
            ));


    public static SortMethod getSortByName(String name) {
        return sortMap.get(name.toLowerCase());
    }
}
