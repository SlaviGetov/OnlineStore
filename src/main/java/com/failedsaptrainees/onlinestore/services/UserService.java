package com.failedsaptrainees.onlinestore.services;

import com.failedsaptrainees.onlinestore.DTO.Forms.RegistrationDTO;
import com.failedsaptrainees.onlinestore.models.UserModel;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {

    public UserModel registerUser(RegistrationDTO registrationDTO);
    public void updateUser(UserModel userModel);
    public UserModel getUserById(Long id) throws UsernameNotFoundException;
    public UserModel getUserByEmail(String email) throws UsernameNotFoundException;
    public List<UserModel> getAllUsers();

}
