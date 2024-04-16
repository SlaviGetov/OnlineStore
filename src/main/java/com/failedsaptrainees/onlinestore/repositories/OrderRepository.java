package com.failedsaptrainees.onlinestore.repositories;

import com.failedsaptrainees.onlinestore.models.OrderModel;
import com.failedsaptrainees.onlinestore.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderModel, Long> {

    public List<OrderModel> findOrdersByUserModel(UserModel userModel);
    public List<OrderModel> findOrdersByOrderDatetimeBetween(LocalDateTime fromDate, LocalDateTime toDate);

}
