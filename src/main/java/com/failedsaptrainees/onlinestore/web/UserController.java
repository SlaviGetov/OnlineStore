package com.failedsaptrainees.onlinestore.web;


import com.failedsaptrainees.onlinestore.DTO.Forms.RegistrationDTO;
import com.failedsaptrainees.onlinestore.exceptions.RegistrationException;
import com.failedsaptrainees.onlinestore.services.UserService;
import com.failedsaptrainees.onlinestore.utils.RedirectAttributeUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage()
    {
        return "userActionPages/login";
    }

    @GetMapping("/register")
    public String registerPage()
    {
        return "userActionPages/registration";
    }

    @PostMapping("/register")
    public String postRegister(@Valid @ModelAttribute RegistrationDTO registrationDTO, BindingResult bindingResult, RedirectAttributes attrs)
    {

        if(bindingResult.hasErrors())
        {

            bindingResult.getAllErrors().forEach(error -> {
                RedirectAttributeUtils.addErrorToModel(attrs, error.getDefaultMessage());
            });

            return "redirect:/user/register";
        }
        try{
            userService.registerUser(registrationDTO);
            return "redirect:/user/login";
        } catch (RegistrationException e)
        {
            RedirectAttributeUtils.addErrorToModel(attrs, "This email has already been taken!");
            return "redirect:/user/register";
        }
    }


}
