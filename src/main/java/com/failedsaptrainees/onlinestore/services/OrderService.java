package com.failedsaptrainees.onlinestore.services;

import com.failedsaptrainees.onlinestore.models.CartProductModel;
import com.failedsaptrainees.onlinestore.models.OrderModel;
import com.failedsaptrainees.onlinestore.models.OrderProductModel;
import com.failedsaptrainees.onlinestore.models.UserModel;

import java.util.List;

public interface OrderService {

    public void sendOrder(List<CartProductModel> cartList);

    public void getOrdersByUser(UserModel user);

    public List<OrderModel> getAllOrders();

    public List<OrderProductModel> getOrderProducts(OrderModel orderModel);

}
