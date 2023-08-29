package com.example.electroscoot.services;

import com.example.electroscoot.configs.RoleServiceTestContextConfiguration;
import com.example.electroscoot.dao.RoleRepository;
import com.example.electroscoot.dto.RoleDTO;
import com.example.electroscoot.entities.Role;
import com.example.electroscoot.services.interfaces.IRoleService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@Import(RoleServiceTestContextConfiguration.class)
public class RoleServiceIntegrationTests {

    @Autowired
    private IRoleService roleService;

    @MockBean
    private RoleRepository roleRepository;

    @Before
    public void setUp() {
        String testName = "testRole";
        String fakeName = "fakeRole";
        Role role = new Role();
        role.setName(testName);

        Mockito.when(roleRepository.save(role)).thenReturn(role);
        Mockito.when(roleRepository.findByName(testName)).thenReturn(Optional.of(role));
        Mockito.when(roleRepository.findByName(fakeName)).thenReturn(null);
    }

    @Test
    public void createTest() {
        String testName = "testRole";
        Role role = new Role();
        role.setName(testName);
        RoleDTO expectedRoleDTO = new RoleDTO(role);

        RoleDTO resultRoleDTO = roleService.create(new RoleDTO(role));

        Assert.assertEquals(expectedRoleDTO, resultRoleDTO);
    }

    @Test
    public void doesExistByNameTest() {
        String testName = "testRole";
        String fakeName = "fakeRole";

        boolean result1 = roleService.doesExistByName(testName);
        boolean result2 = roleService.doesExistByName(fakeName);

        Assert.assertTrue(result1);

        Assert.assertFalse(result2);
    }
}
