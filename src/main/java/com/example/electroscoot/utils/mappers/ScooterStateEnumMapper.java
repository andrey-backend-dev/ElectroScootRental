package com.example.electroscoot.utils.mappers;

import com.example.electroscoot.utils.enums.ScooterStateEnum;

import java.util.HashMap;
import java.util.Map;

public class ScooterStateEnumMapper {
    private static final Map<String, ScooterStateEnum> sortMap = new HashMap<>(Map.of(
            ScooterStateEnum.OK.getName(), ScooterStateEnum.OK,
            ScooterStateEnum.BROKEN.getName(), ScooterStateEnum.BROKEN,
            ScooterStateEnum.RENTED.getName(), ScooterStateEnum.RENTED
    ));


    public static ScooterStateEnum getScooterStateByName(String name) {
        return sortMap.getOrDefault(name.toLowerCase(), ScooterStateEnum.NULL);
    }
}
