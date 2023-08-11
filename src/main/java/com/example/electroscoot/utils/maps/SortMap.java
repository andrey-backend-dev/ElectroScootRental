package com.example.electroscoot.utils.maps;

import com.example.electroscoot.utils.enums.SortMethod;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.Map;

public class SortMap {
    private static final Map<String, SortMethod> sortMap = new HashMap<>(Map.of(
            SortMethod.ADDRESS.getName(), SortMethod.ADDRESS,
            SortMethod.STATE.getName(), SortMethod.STATE
            ));


    public static SortMethod getSortByName(String name) {
        return sortMap.getOrDefault(name.toLowerCase(), SortMethod.NULL);
    }
}
