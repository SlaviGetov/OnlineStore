package com.failedsaptrainees.onlinestore.services;

import com.failedsaptrainees.onlinestore.models.OrderModel;
import com.failedsaptrainees.onlinestore.models.OrderProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService{


    @Autowired
    private OrderService orderService;

    @Override
    public Double getIncomeBetweenTwoDates(LocalDateTime fromDate, LocalDateTime toDate) {

        double result = 0;
        List<OrderModel> orders = orderService.getAllOrdersBetweenDates(fromDate, toDate);

        for (OrderModel order : orders) {
           List<OrderProductModel> orderProducts = orderService.getOrderProducts(order);
            for (OrderProductModel orderProduct : orderProducts) {
                result += orderProduct.getPriceAtTime() * orderProduct.getAmount();
            }
        }

        return  result;
    }
}
