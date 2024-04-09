package com.failedsaptrainees.onlinestore.services;


import com.failedsaptrainees.onlinestore.enums.Roles;
import com.failedsaptrainees.onlinestore.models.RoleModel;

public interface RoleService {

    public void InitializeRoles();
    public RoleModel getRole(Roles roles);

}
