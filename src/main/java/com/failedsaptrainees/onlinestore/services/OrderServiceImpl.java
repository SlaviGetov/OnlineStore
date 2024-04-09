package com.failedsaptrainees.onlinestore.services;

import com.failedsaptrainees.onlinestore.models.CartProductModel;
import com.failedsaptrainees.onlinestore.models.OrderModel;
import com.failedsaptrainees.onlinestore.models.OrderProductModel;
import com.failedsaptrainees.onlinestore.models.UserModel;
import com.failedsaptrainees.onlinestore.repositories.OrderProductRepository;
import com.failedsaptrainees.onlinestore.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private UserService userService;

    @Override
    public void sendOrder(List<CartProductModel> cartList) {

        UserModel userModel = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        OrderModel orderModel = new OrderModel(
                userModel,
                LocalDateTime.now()
        );

        orderRepository.saveAndFlush(orderModel);

        for (CartProductModel cartItem : cartList) {
            OrderProductModel orderProductModel = new OrderProductModel(
                    orderModel,
                    cartItem.getProduct(),
                    cartItem.getAmount(),
                    cartItem.getProduct().getCurrentPrice()
            );

            orderProductRepository.save(orderProductModel);
        }

        orderProductRepository.flush();
    }

    @Override
    public List<OrderModel> getOrdersByUser(UserModel user) {
        return orderRepository.findOrdersByUserModel(user);
    }

    @Override
    public List<OrderModel> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<OrderProductModel> getOrderProducts(OrderModel orderModel) {
        return orderProductRepository.findOrderProductsByOrderModel(orderModel);
    }
}
