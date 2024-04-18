package com.failedsaptrainees.onlinestore.models;


import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@NoArgsConstructor
@Entity(name = "discounts")
public class DiscountModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String discountName;

    private boolean isActive;

    private double percentageDiscount;

    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ProductModel> products;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public double getPercentageDiscount() {
        return percentageDiscount;
    }

    public void setPercentageDiscount(double percentageDiscount) {
        this.percentageDiscount = percentageDiscount;
    }

    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public DiscountModel(String discountName, boolean isActive, double percentageDiscount, List<ProductModel> products) {
        this.discountName = discountName;
        this.isActive = isActive;
        this.percentageDiscount = percentageDiscount;
        this.products = products;
    }
}
