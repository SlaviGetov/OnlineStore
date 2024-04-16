package com.failedsaptrainees.onlinestore.eventlisteners;

import com.failedsaptrainees.onlinestore.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartupEvent {

    @Autowired
    private RoleService roleService;


    @EventListener(ApplicationReadyEvent.class)
    public void setupRoles()
    {
        roleService.InitializeRoles();
    }


}
