package com.failedsaptrainees.onlinestore.repositories;

import com.failedsaptrainees.onlinestore.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;


//TODO: Add and use a RoleService instead of directly accessing the repository.
public interface RoleRepository extends JpaRepository<RoleModel, Long> {

    RoleModel findByName(String name);

}
