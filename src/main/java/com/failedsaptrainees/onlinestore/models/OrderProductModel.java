package com.failedsaptrainees.onlinestore.models;


import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity(name = "orders_products")
public class OrderProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private OrderModel orderModel;

    @ManyToOne
    private ProductModel productModel;

    private int amount;

    private Double priceAtTime;

    public OrderProductModel(OrderModel orderModel, ProductModel productModel, int amount, Double priceAtTime) {
        this.orderModel = orderModel;
        this.amount = amount;
        this.productModel = productModel;
        this.priceAtTime = priceAtTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderModel getOrderModel() {
        return orderModel;
    }

    public void setOrderModel(OrderModel orderModel) {
        this.orderModel = orderModel;
    }

    public ProductModel getProductModel() {
        return productModel;
    }

    public void setProductModel(ProductModel productModel) {
        this.productModel = productModel;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Double getPriceAtTime() {
        return priceAtTime;
    }

    public void setPriceAtTime(Double priceAtTime) {
        this.priceAtTime = priceAtTime;
    }
}
