package com.failedsaptrainees.onlinestore.repositories;

import com.failedsaptrainees.onlinestore.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findByEmail(String email);

}
