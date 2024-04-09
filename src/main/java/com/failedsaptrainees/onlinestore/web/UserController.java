package com.failedsaptrainees.onlinestore.web;


import com.failedsaptrainees.onlinestore.DTO.Forms.RegistrationDTO;
import com.failedsaptrainees.onlinestore.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

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
    public String postRegister(@Valid @ModelAttribute RegistrationDTO registrationDTO, BindingResult bindingResult)
    {

        if(bindingResult.hasErrors())
        {
            for (ObjectError allError : bindingResult.getAllErrors()) {
                System.out.println(allError.getDefaultMessage());
            }

            return "redirect:/user/register";
        }

        userService.registerUser(registrationDTO);
        return "redirect:/user/login";
    }


}
