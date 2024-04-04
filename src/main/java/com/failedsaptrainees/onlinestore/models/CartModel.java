package com.failedsaptrainees.onlinestore.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "carts")
public class CartModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @OneToOne
    public UserModel user;

    //TODO: Add an "amount" column to the many-to-many relationship. Possibly will need to use a new model.
    @ManyToMany
    public List<ProductModel> products = new ArrayList<>();

}
