package com.failedsaptrainees.onlinestore.services;

import com.failedsaptrainees.onlinestore.DTO.Forms.RegistrationDTO;
import com.failedsaptrainees.onlinestore.models.UserModel;

public interface UserService {

    public void registerUser(RegistrationDTO registrationDTO);

    public UserModel getUserByEmail(String email);

}
