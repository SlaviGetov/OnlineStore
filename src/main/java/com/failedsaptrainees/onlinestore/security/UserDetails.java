package com.failedsaptrainees.onlinestore.security;

import com.failedsaptrainees.onlinestore.models.UserModel;
import com.failedsaptrainees.onlinestore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetails implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findByEmail(username);
        if(userModel != null)
        {
            User user = new User(
                    userModel.getEmail(),
                    userModel.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority(userModel.getRole().getName()))
            );

            return user;
        } else
        {
            throw new UsernameNotFoundException("Invalid email or password!");
        }
    }
}
