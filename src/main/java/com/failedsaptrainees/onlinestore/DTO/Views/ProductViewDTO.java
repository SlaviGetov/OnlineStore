package com.failedsaptrainees.onlinestore.DTO.Views;

import com.failedsaptrainees.onlinestore.models.ProductModel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;

@NoArgsConstructor
public class ProductViewDTO {

    private Long id;
    private String name;
    private Double currentPrice;
    private Double defaultPrice;
    private Double minimumPrice;
    private Long stockAmount;
    private String category;
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

    public ProductViewDTO(Long id, String name, Double currentPrice, Double defaultPrice, Double minimumPrice, Long stockAmount, String imageLink, String category) {
        this.id = id;
        this.name = name;
        this.currentPrice = currentPrice;
        this.defaultPrice = defaultPrice;
        this.minimumPrice = minimumPrice;
        this.stockAmount = stockAmount;
        this.imageLink = imageLink;
        this.category = category;
    }

    public ProductViewDTO(ProductModel productModel, Double currentPrice)
    {
        this(
                productModel.getId(),
                productModel.getName(),
                currentPrice,
                productModel.getDefaultPrice(),
                productModel.getMinimumPrice(),
                productModel.getStockAmount(),
                productModel.getImageLink(),
                productModel.getCategory().getName()
        );
    }

    public String getDefaultPriceFormatted()
    {
        DecimalFormat df = new DecimalFormat("$#0.00");
        return df.format(getDefaultPrice());
    }

    public String getMinimumPriceFormatted()
    {
        DecimalFormat df = new DecimalFormat("$#0.00");
        return df.format(getMinimumPrice());
    }

    public String getCurrentPriceFormatted()
    {
        DecimalFormat df = new DecimalFormat("$#0.00");
        return df.format(getCurrentPrice());
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
