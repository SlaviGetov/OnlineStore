package com.failedsaptrainees.onlinestore.repositories;

import com.failedsaptrainees.onlinestore.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<RoleModel, Long> {

    RoleModel findByName(String name);


}
