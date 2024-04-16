package com.failedsaptrainees.onlinestore.services;

import com.failedsaptrainees.onlinestore.exceptions.OrderException;
import com.failedsaptrainees.onlinestore.models.CartProductModel;
import com.failedsaptrainees.onlinestore.models.OrderModel;
import com.failedsaptrainees.onlinestore.models.OrderProductModel;
import com.failedsaptrainees.onlinestore.models.UserModel;
import com.failedsaptrainees.onlinestore.repositories.OrderProductRepository;
import com.failedsaptrainees.onlinestore.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;


    @Override
    public void sendOrder(List<CartProductModel> cartList) throws OrderException {

        UserModel userModel = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        for (CartProductModel cartItem : cartList) {
            if(cartItem.getProduct().getStockAmount() < cartItem.getAmount())
            {
                throw new OrderException("One ore more products in your basket is not in stock!");
            }
        }

        OrderModel orderModel = new OrderModel(
                userModel,
                LocalDateTime.now()
        );

        orderRepository.save(orderModel);

        for (CartProductModel cartItem : cartList) {

            cartItem.getProduct().setStockAmount(cartItem.getProduct().getStockAmount() - cartItem.getAmount());
            productService.updateProduct(cartItem.getProduct().getId(), cartItem.getProduct());

            OrderProductModel orderProductModel = new OrderProductModel(
                    orderModel,
                    cartItem.getProduct(),
                    cartItem.getAmount(),
                    productService.getProductCurrentPrice(cartItem.getProduct())
            );

            orderProductRepository.save(orderProductModel);
        }
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
    public List<OrderModel> getAllOrdersOrdered() {
        return orderRepository.findAllByOrderByIdDesc();
    }

    @Override
    public List<OrderProductModel> getOrderProducts(OrderModel orderModel) {
        return orderProductRepository.findOrderProductsByOrderModel(orderModel);
    }

    @Override
    public List<OrderModel> getAllOrdersBetweenDates(LocalDateTime fromDate, LocalDateTime toDate) {
        return orderRepository.findOrdersByOrderDatetimeBetween(fromDate, toDate);
    }
}
