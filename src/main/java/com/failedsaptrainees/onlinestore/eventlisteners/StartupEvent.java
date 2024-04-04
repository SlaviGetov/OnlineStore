package com.failedsaptrainees.onlinestore.eventlisteners;

import com.failedsaptrainees.onlinestore.models.RoleModel;
import com.failedsaptrainees.onlinestore.repositories.RoleRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class StartupEvent {

    @Autowired
    private RoleRepository roleRepository;


    @EventListener(ApplicationReadyEvent.class)
    public void setupRoles()
    {
        if(roleRepository.count() == 0)
        {
            roleRepository.save(new RoleModel("ADMIN"));
            roleRepository.save(new RoleModel("EMPLOYEE"));
            roleRepository.save(new RoleModel("CLIENT"));
        }
    }


}
