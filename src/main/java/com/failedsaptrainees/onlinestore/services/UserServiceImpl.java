package com.failedsaptrainees.onlinestore.services;

import com.failedsaptrainees.onlinestore.DTO.Forms.RegistrationDTO;
import com.failedsaptrainees.onlinestore.enums.Roles;
import com.failedsaptrainees.onlinestore.models.UserModel;
import com.failedsaptrainees.onlinestore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(RegistrationDTO registrationDTO) {

        Roles role = Roles.CLIENT;

        if(userRepository.count() == 0)
        {
            role = Roles.ADMIN;
        }

        UserModel userModel = new UserModel(
                registrationDTO.getFullName(),
                registrationDTO.getEmail(),
                passwordEncoder.encode(registrationDTO.getPassword()),
                "0",
                registrationDTO.getDateOfBirth(),
                registrationDTO.getGender(),
                roleService.getRole(role)
        );
        userRepository.saveAndFlush(userModel);
    }

    @Override
    public UserModel getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
