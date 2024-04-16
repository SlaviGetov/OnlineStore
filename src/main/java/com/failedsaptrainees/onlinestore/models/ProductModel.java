package com.failedsaptrainees.onlinestore.models;

import com.failedsaptrainees.onlinestore.DTO.Views.ProductViewDTO;
import com.failedsaptrainees.onlinestore.exceptions.ProductException;
import com.failedsaptrainees.onlinestore.services.CategoryService;
import com.failedsaptrainees.onlinestore.services.DiscountService;
import com.failedsaptrainees.onlinestore.services.DiscountServiceImpl;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "products")
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageLink;
    @ManyToOne
    private CategoryModel category;
    private String name;
    private Double defaultPrice;
    private Double minimumPrice;
    private Long stockAmount;

    public ProductModel(String imageLink, String name, Double defaultPrice, Double minimumPrice, Long stockAmount, CategoryModel category) throws ProductException {
        this.imageLink = imageLink;
        this.name = name;

        if(minimumPrice > defaultPrice)
        {
            throw new ProductException("Minimum price cannot be higher than default price");
        }

        this.defaultPrice = defaultPrice;
        this.minimumPrice = minimumPrice;
        this.stockAmount = stockAmount;
        this.category = category;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(Double defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public Double getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(Double minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public Long getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(Long stockAmount) {
        this.stockAmount = stockAmount;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }
}
