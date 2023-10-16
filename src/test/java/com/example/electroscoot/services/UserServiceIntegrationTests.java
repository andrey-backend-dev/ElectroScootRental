package com.example.electroscoot.services;

import com.example.electroscoot.configs.UserServiceTestContextConfiguration;
import com.example.electroscoot.dao.RoleRepository;
import com.example.electroscoot.dao.UserRepository;
import com.example.electroscoot.dto.*;
import com.example.electroscoot.entities.Role;
import com.example.electroscoot.entities.User;
import com.example.electroscoot.services.interfaces.IAuthenticationService;
import com.example.electroscoot.services.interfaces.IUserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RunWith(SpringRunner.class)
@Import(UserServiceTestContextConfiguration.class)
@PropertySource("classpath:application.properties")
public class UserServiceIntegrationTests {

    @Autowired
    private IUserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private IAuthenticationService authenticationService;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private Clock clock;

    private final Clock fixedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

    @Value("${business.defaultRole}")
    private String defaultRole;

    @Before
    public void setUp() {
//        init data
        String username = "testUsername";
        String password = "testPassword";
        String phone = "testPhone";

//        creation

        Role role = new Role();
        role.setName(defaultRole);

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);
        user.setRoles(new HashSet<>(Set.of(role)));
        user.setRegisteredSince(LocalDateTime.now(fixedClock));

//        mockito's when

        Mockito.when(clock.instant()).thenReturn(fixedClock.instant());

        Mockito.when(clock.getZone()).thenReturn(fixedClock.getZone());

        Mockito.when(roleRepository.findByName(defaultRole)).thenReturn(Optional.of(role));

        Mockito.when(userRepository.save(user)).thenReturn(user);

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

    }

    @Test
    public void registerTest() {
        String username = "testUsername";
        String password = "testPassword";
        String phone = "testPhone";

        RegistrationDTO registrationDTO = new RegistrationDTO(username, password, phone);

        User user = new User();
        user.setUsername(username);
        user.setPhone(phone);
        user.setRegisteredSince(LocalDateTime.now(fixedClock));

        UserDTO expectedUserDTO = new UserDTO(user);

        AuthenticationDTO resultUserDTO = authenticationService.register(registrationDTO);

        Assert.assertEquals(expectedUserDTO, resultUserDTO);
    }

    @Test
    public void updateByUsernameTest() {
        String otherUsername = "other username";
        String newFirstname = "Vasiliy";
        String phone = "testPhone";

        UpdateUserDTO updateData = new UpdateUserDTO(otherUsername, null, newFirstname, null,
                null);

        User user = new User();
        user.setUsername(otherUsername);
        user.setPhone(phone);
        user.setRegisteredSince(LocalDateTime.now(fixedClock));
        user.setFirstname(newFirstname);

        UserDTO expectedUserDTO = new UserDTO(user);

        User resultUserDTO = userService.updateByUsername("testUsername", updateData);

        Assert.assertEquals(expectedUserDTO, resultUserDTO);
    }
}
