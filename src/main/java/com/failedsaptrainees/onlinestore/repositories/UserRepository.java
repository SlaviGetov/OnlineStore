package com.failedsaptrainees.onlinestore.repositories;

import com.failedsaptrainees.onlinestore.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {

    UserModel findByEmail(String email);

}
