package com.failedsaptrainees.onlinestore.DTO.Views;

public class CartViewDTO {

    private Long productId;
    private String name;
    private Double currentPrice;
    private int amount;


    public CartViewDTO(Long productId, String name, Double currentPrice, int amount) {
        this.productId = productId;
        this.name = name;
        this.currentPrice = currentPrice;
        this.amount = amount;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long id) {
        this.productId = id;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
