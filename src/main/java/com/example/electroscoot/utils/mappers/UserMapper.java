package com.example.electroscoot.utils.mappers;

import com.example.electroscoot.dto.RegistrationDTO;
import com.example.electroscoot.dto.UserDTO;
import com.example.electroscoot.entities.Role;
import com.example.electroscoot.entities.User;
import com.example.electroscoot.utils.enums.UserStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    //if (user.getScooter() != null)
    //            this.scooterId = user.getScooter().getId();
    User registrationToUser(String username,
                               String password,
                               String phone,
                               UserStatus status,
                               HashSet<Role> roles,
                               LocalDateTime registeredSince);

    UserDTO userToUserDto(User user);

    List<UserDTO> userToUserDto(Iterable<User> users);
}
