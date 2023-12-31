package com.example.electroscoot.utils.mappers;

import com.example.electroscoot.utils.enums.UserStatus;

import java.util.HashMap;
import java.util.Map;

public class UserStatusEnumMapper {
    private static final Map<String, UserStatus> map = new HashMap<>(Map.of(
            UserStatus.OK.getName(), UserStatus.OK,
            UserStatus.BANNED.getName(), UserStatus.BANNED
    ));

    public static UserStatus getStatusByName(String name) {
        return map.get(name.toUpperCase());
    }
}
