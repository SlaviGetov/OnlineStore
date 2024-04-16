package com.failedsaptrainees.onlinestore.integrationTests;

import com.failedsaptrainees.onlinestore.DTO.Forms.RegistrationDTO;
import com.failedsaptrainees.onlinestore.enums.Roles;
import com.failedsaptrainees.onlinestore.exceptions.RegistrationException;
import com.failedsaptrainees.onlinestore.models.RoleModel;
import com.failedsaptrainees.onlinestore.models.UserModel;
import com.failedsaptrainees.onlinestore.repositories.UserRepository;
import com.failedsaptrainees.onlinestore.services.RoleService;
import com.failedsaptrainees.onlinestore.services.UserServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.management.relation.Role;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserServiceImpl userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private List<UserModel> users;

    @BeforeEach
    public void setup()
    {
        users = new ArrayList<>();
        when(userRepository.findAll())
                .thenReturn(users);


        when(roleService.getRole(Roles.ADMIN)).thenReturn(new RoleModel(Roles.ADMIN));
        when(roleService.getRole(Roles.CLIENT)).thenReturn(new RoleModel(Roles.CLIENT));
        when(roleService.getRole(Roles.EMPLOYEE)).thenReturn(new RoleModel(Roles.EMPLOYEE));
    }

    @Test
    public void firstUser_gets_admin() throws RegistrationException
    {
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setEmail("test@mail.com");
        registrationDTO.setPassword("123");
        registrationDTO.setGender("male");
        registrationDTO.setFullName("Test Name");

        registrationDTO.setDateOfBirth(new Date(2003, 04, 11));

        UserModel userModel = userService.registerUser(registrationDTO);

        assertEquals(userModel.getFullName(), "Test Name");
        assertEquals(userModel.getGender(), "male");
        assertTrue(passwordEncoder.matches("123", userModel.getPassword()));
        assertEquals(userModel.getEmail(), "test@mail.com");
        assertEquals(userModel.getRole().getRoleName(), Roles.ADMIN.toString());
    }

    @Test
    public void notFirstUser_gets_client() throws RegistrationException
    {
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setEmail("test@mail.com");
        registrationDTO.setPassword("123");
        registrationDTO.setGender("male");
        registrationDTO.setFullName("Test Name");
        registrationDTO.setDateOfBirth(new Date(2003, 04, 11));

        when(userRepository.count()).thenReturn(1L);
        UserModel userModel = userService.registerUser(registrationDTO);

        assertEquals(userModel.getFullName(), "Test Name");
        assertEquals(userModel.getGender(), "male");
        assertTrue(passwordEncoder.matches("123", userModel.getPassword()));
        assertEquals(userModel.getEmail(), "test@mail.com");
        assertEquals(userModel.getRole().getRoleName(), Roles.CLIENT.toString());
    }

    @Test
    public void getUserById_throwsError_whenNoUser()
    {
        assertThrows(UsernameNotFoundException.class, () -> {
           userService.getUserById(1L);
        });
    }

    @Test
    public void getUserByEmail_throwsError_whenNoUser()
    {
        assertThrows(UsernameNotFoundException.class, () -> {
            userService.getUserByEmail("test@mail.com");
        });
    }

    @Test
    public void getAllUsers_returns_listOfUsers()
    {
        assertEquals(users, userService.getAllUsers());
    }

}
