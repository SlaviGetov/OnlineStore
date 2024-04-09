package com.failedsaptrainees.onlinestore.models;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Entity(name = "orders")
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private UserModel userModel;

    private LocalDateTime orderDatetime;

    public OrderModel(UserModel userModel, LocalDateTime orderDatetime) {
        this.userModel = userModel;
        this.orderDatetime = orderDatetime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public LocalDateTime getOrderDatetime() {
        return orderDatetime;
    }

    public void setOrderDatetime(LocalDateTime orderDatetime) {
        this.orderDatetime = orderDatetime;
    }
}
