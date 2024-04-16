package com.failedsaptrainees.onlinestore.DTO.Views;

import com.failedsaptrainees.onlinestore.models.OrderModel;
import com.failedsaptrainees.onlinestore.models.OrderProductModel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.query.Order;

import java.util.List;

@NoArgsConstructor
public class OrderViewDTO {

    private OrderModel orderModel;

    private List<OrderProductModel> orderProductModelList;

    public OrderModel getOrderModel() {
        return orderModel;
    }

    public void setOrderModel(OrderModel orderModel) {
        this.orderModel = orderModel;
    }

    public List<OrderProductModel> getOrderProductModelList() {
        return orderProductModelList;
    }

    public void setOrderProductModelList(List<OrderProductModel> orderProductModelList) {
        this.orderProductModelList = orderProductModelList;
    }

    public OrderViewDTO(OrderModel orderModel, List<OrderProductModel> orderProductModelList) {
        this.orderModel = orderModel;
        this.orderProductModelList = orderProductModelList;
    }
}
