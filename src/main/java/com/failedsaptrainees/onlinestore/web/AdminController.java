package com.failedsaptrainees.onlinestore.web;

import com.failedsaptrainees.onlinestore.DTO.Forms.UserDetailsFormDTO;
import com.failedsaptrainees.onlinestore.DTO.Views.UserDetailsViewDTO;
import com.failedsaptrainees.onlinestore.enums.Roles;
import com.failedsaptrainees.onlinestore.models.RoleModel;
import com.failedsaptrainees.onlinestore.models.UserModel;
import com.failedsaptrainees.onlinestore.security.SecurityConfig;
import com.failedsaptrainees.onlinestore.services.RoleService;
import com.failedsaptrainees.onlinestore.services.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("")
    public String getAllUsers(Model model)
    {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/userList";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable(name = "id") Long userId, Model model)
    {

        try{

            ModelMapper modelMapper = new ModelMapper();
            UserModel userModel = userService.getUserById(userId);
            UserDetailsViewDTO userDetailsViewDTO = modelMapper.map(userModel, UserDetailsViewDTO.class);
            model.addAttribute("userDetailsViewDTO", userDetailsViewDTO);
            model.addAttribute("userId", userId);

            return "admin/editUser";
        } catch (UsernameNotFoundException e)
        {
            return "redirect:/admin";
        }
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable(name = "id") Long userId, @Valid @ModelAttribute UserDetailsFormDTO userDetailsFormDTO, BindingResult bindingResult)
    {
        try{
            Roles.valueOf(userDetailsFormDTO.getRole());
        } catch (IllegalArgumentException e)
        {
            bindingResult.rejectValue("role", "invalidRole", "The specified role is invalid");
        }

        if(!bindingResult.hasErrors())
        {
            try{
                UserModel userModel = userService.getUserById(userId);
                userModel.setFullName(userDetailsFormDTO.getFullName());
                userModel.setRole(roleService.getRole(Roles.valueOf(userDetailsFormDTO.getRole())));

                if(!userDetailsFormDTO.getPassword().isEmpty())
                {
                    userModel.setPassword(SecurityConfig.passwordEncoder().encode(userDetailsFormDTO.getPassword()));
                }

                userService.updateUser(userModel.getId(), userModel);
            } catch (UsernameNotFoundException e)
            {
                return "redirect:/admin";
            }
        }

        for (ObjectError allError : bindingResult.getAllErrors()) {
            System.out.println(allError.getDefaultMessage());
        }


        return "redirect:/admin/edit/" + userId;
    }
}
