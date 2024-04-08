package com.failedsaptrainees.onlinestore.services;

import com.failedsaptrainees.onlinestore.DTO.Forms.RegistrationDTO;
import com.failedsaptrainees.onlinestore.models.UserModel;
import com.failedsaptrainees.onlinestore.repositories.RoleRepository;
import com.failedsaptrainees.onlinestore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(RegistrationDTO registrationDTO) {

        String roleName = "CLIENT";

        if(userRepository.count() == 0)
        {
            roleName = "ADMIN";
        }

        UserModel userModel = new UserModel(
                registrationDTO.getFullName(),
                registrationDTO.getEmail(),
                passwordEncoder.encode(registrationDTO.getPassword()),
                "0",
                registrationDTO.getDateOfBirth(),
                registrationDTO.getGender(),
                roleRepository.findByName(roleName)
        );
        userRepository.saveAndFlush(userModel);
    }

    @Override
    public UserModel getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
