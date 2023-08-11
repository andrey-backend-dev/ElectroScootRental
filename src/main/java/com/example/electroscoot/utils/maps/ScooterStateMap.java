package com.example.electroscoot.utils.maps;

import com.example.electroscoot.utils.enums.ScooterStateEnum;
import com.example.electroscoot.utils.enums.SortMethod;

import java.util.HashMap;
import java.util.Map;

public class ScooterStateMap {
    private static final Map<String, ScooterStateEnum> sortMap = new HashMap<>(Map.of(
            ScooterStateEnum.OK.getName(), ScooterStateEnum.OK,
            ScooterStateEnum.BROKEN.getName(), ScooterStateEnum.BROKEN,
            ScooterStateEnum.RENTED.getName(), ScooterStateEnum.RENTED
    ));


    public static ScooterStateEnum getScooterStateByName(String name) {
        return sortMap.getOrDefault(name.toLowerCase(), ScooterStateEnum.NULL);
    }
}
