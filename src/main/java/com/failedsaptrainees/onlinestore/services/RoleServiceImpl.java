package com.failedsaptrainees.onlinestore.services;

import com.failedsaptrainees.onlinestore.enums.Roles;
import com.failedsaptrainees.onlinestore.models.RoleModel;
import com.failedsaptrainees.onlinestore.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void InitializeRoles() {

        if(roleRepository.count() == 0)
        {
            roleRepository.save(new RoleModel(Roles.ADMIN.toString()));
            roleRepository.save(new RoleModel(Roles.EMPLOYEE.toString()));
            roleRepository.save(new RoleModel(Roles.CLIENT.toString()));
        }
    }

    @Override
    public RoleModel getRole(Roles roles) {
        return roleRepository.findByName(roles.toString());
    }
}
