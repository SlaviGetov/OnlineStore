package com.failedsaptrainees.onlinestore.web;


import com.failedsaptrainees.onlinestore.DTO.RegistrationDTO;
import com.failedsaptrainees.onlinestore.repositories.RoleRepository;
import com.failedsaptrainees.onlinestore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage()
    {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage()
    {
        return "registration";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute RegistrationDTO registrationDTO)
    {
        userService.registerUser(registrationDTO);
        return "redirect:/user/login";
    }


}
