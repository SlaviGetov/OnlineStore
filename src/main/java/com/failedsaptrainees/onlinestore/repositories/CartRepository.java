package com.failedsaptrainees.onlinestore.repositories;

import com.failedsaptrainees.onlinestore.models.CartModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartModel, Long> {


}
