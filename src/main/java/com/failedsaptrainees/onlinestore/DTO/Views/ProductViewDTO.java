package com.failedsaptrainees.onlinestore.DTO.Views;

import com.failedsaptrainees.onlinestore.models.ProductModel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ProductViewDTO {

    private Long id;
    private String name;
    private Double currentPrice;
    private Double defaultPrice;
    private Double minimumPrice;
    private Long stockAmount;
    private String imageLink;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
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

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public ProductViewDTO(Long id, String name, Double currentPrice, Double defaultPrice) {
        this.id = id;
        this.name = name;
        this.currentPrice = currentPrice;
        this.defaultPrice = defaultPrice;
    }

    public ProductViewDTO(ProductModel productModel, Double currentPrice)
    {
        this(productModel.getId(), productModel.getName(), currentPrice, productModel.getDefaultPrice());
    }



}
