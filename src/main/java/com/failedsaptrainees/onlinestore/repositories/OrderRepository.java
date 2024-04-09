package com.failedsaptrainees.onlinestore.repositories;

import com.failedsaptrainees.onlinestore.models.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderModel, Long> {
}
