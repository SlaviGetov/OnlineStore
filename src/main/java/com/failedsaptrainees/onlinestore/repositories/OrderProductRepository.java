package com.failedsaptrainees.onlinestore.repositories;

import com.failedsaptrainees.onlinestore.models.OrderModel;
import com.failedsaptrainees.onlinestore.models.OrderProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProductModel, Long> {

    public List<OrderProductModel> findOrderProductsByOrderModel(OrderModel orderModel);

}
