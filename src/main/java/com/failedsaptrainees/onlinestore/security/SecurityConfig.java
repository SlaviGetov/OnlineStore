package com.failedsaptrainees.onlinestore.security;

import com.failedsaptrainees.onlinestore.eventlisteners.AuthenticationSuccessListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig  {

    @Autowired
    private UserDetails userDetails;

    @Autowired
    private AuthenticationSuccessListener authenticationSuccessListener;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        http.csrf(AbstractHttpConfigurer::disable)
                        .formLogin(httpSecurityFormLoginConfigurer ->
                                httpSecurityFormLoginConfigurer.loginPage("/user/login")
                                        .loginProcessingUrl("/user/login")
                                        .defaultSuccessUrl("/")
                                        .failureUrl("/user/login?error")
                                        .usernameParameter("email")
                                        .successHandler(authenticationSuccessListener)
                        ).logout(httpSecurityLogoutConfigurer ->
                            httpSecurityLogoutConfigurer.logoutUrl("/user/logout")
                        );

        http.addFilterAfter(pathMatchFilter(), BasicAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PathMatchFilter pathMatchFilter()
    {
        return new PathMatchFilter();
    }

    @Bean
    public static PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }


    public void configure(AuthenticationManagerBuilder builder) throws Exception{

        builder.userDetailsService(userDetails).passwordEncoder(passwordEncoder());

    }

}