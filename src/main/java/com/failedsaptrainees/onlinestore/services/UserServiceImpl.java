package com.failedsaptrainees.onlinestore.services;

import com.failedsaptrainees.onlinestore.DTO.Forms.RegistrationDTO;
import com.failedsaptrainees.onlinestore.enums.Roles;
import com.failedsaptrainees.onlinestore.exceptions.RegistrationException;
import com.failedsaptrainees.onlinestore.models.UserModel;
import com.failedsaptrainees.onlinestore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserModel registerUser(RegistrationDTO registrationDTO) throws RegistrationException {

        Roles role = Roles.CLIENT;

        if(userRepository.count() == 0)
        {
            role = Roles.ADMIN;
        }

        if(userRepository.findByEmail(registrationDTO.getEmail()).isPresent())
        {
            throw new RegistrationException("This email is already taken!");
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
        return userModel;
    }

    @Override
    public UserModel getUserByEmail(String email) {
        Optional<UserModel> userModel = userRepository.findByEmail(email);

        if(userModel.isEmpty())
            throw new UsernameNotFoundException("Email not found!");

        return userModel.get();
    }

    @Override
    public UserModel getUserById(Long id) throws UsernameNotFoundException {

        Optional<UserModel> userModel = userRepository.findById(id);
        if(userModel.isEmpty())
            throw new UsernameNotFoundException("The specified user cannot be found!");

        return userModel.get();
    }

    @Override
    public void updateUser(Long id, UserModel userModel) {
        userModel.setId(id);
        userRepository.saveAndFlush(userModel);
    }

    @Override
    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }
}
