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
import java.util.Optional;

@Service
public class UserDetails implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> userModel = userRepository.findByEmail(username);
        if(userModel.isPresent())
        {
            User user = new User(
                    userModel.get().getEmail(),
                    userModel.get().getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority(userModel.get().getRole().getName()))
            );

            return user;
        } else
        {
            throw new UsernameNotFoundException("Invalid email or password!");
        }
    }
}
