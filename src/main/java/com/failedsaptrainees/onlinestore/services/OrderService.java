package com.failedsaptrainees.onlinestore.services;

import com.failedsaptrainees.onlinestore.exceptions.OrderException;
import com.failedsaptrainees.onlinestore.models.CartProductModel;
import com.failedsaptrainees.onlinestore.models.OrderModel;
import com.failedsaptrainees.onlinestore.models.OrderProductModel;
import com.failedsaptrainees.onlinestore.models.UserModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface OrderService {

    public void sendOrder(List<CartProductModel> cartList) throws OrderException;

    public List<OrderModel> getOrdersByUser(UserModel user);

    public List<OrderModel> getAllOrders();
    public List<OrderModel> getAllOrdersOrdered();

    public List<OrderProductModel> getOrderProducts(OrderModel orderModel);

    public List<OrderModel> getAllOrdersBetweenDates(LocalDateTime fromDate, LocalDateTime toDate);

}
