package com.failedsaptrainees.onlinestore.models;

import com.failedsaptrainees.onlinestore.DTO.Views.ProductViewDTO;
import com.failedsaptrainees.onlinestore.exceptions.ProductException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "products")
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageLink;
    private String name;
    private Double defaultPrice;
    private Double minimumPrice;
    private Long stockAmount;

    public ProductViewDTO getProductViewDTO(){
        ProductViewDTO productViewDTO = new ProductViewDTO(
                id,
                name,
                getCurrentPrice(),
                defaultPrice
        );

        return productViewDTO;
    }


    public ProductModel(String imageLink, String name, Double defaultPrice, Double minimumPrice, Long stockAmount) throws ProductException {
        this.imageLink = imageLink;
        this.name = name;

        if(minimumPrice > defaultPrice)
        {
            throw new ProductException("Minimum price cannot be higher than default price");
        }

        this.defaultPrice = defaultPrice;
        this.minimumPrice = minimumPrice;
        this.stockAmount = stockAmount;
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


    // TODO: Calculate the current price based on any discounts which are currently active for this product.
    // Make sure the new price doesn't drop under the minimum price.
    public Double getCurrentPrice() {
        return defaultPrice;
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
}
