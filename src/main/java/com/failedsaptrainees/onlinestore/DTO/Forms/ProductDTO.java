package com.failedsaptrainees.onlinestore.DTO.Forms;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {

    @NotEmpty(message = "You need to provide a product name")
    private String name;
    @NotNull(message = "A default price is required.")
    @PositiveOrZero(message = "The default price needs to be 0 or more.")
    private Double defaultPrice;
    @NotNull(message = "A minimum price is required.")
    @PositiveOrZero(message = "The minimum price needs to be 0 or more.")
    private Double minimumPrice;
    @NotNull(message = "The amount in stock is required.")
    @PositiveOrZero(message = "The amount in stock needs to be 0 or more.")
    private Long stockAmount;
    @NotEmpty(message = "An image link needs to be provided.")
    private String imageLink;

    @NotEmpty(message = "You need to provide a category for the product")
    private String category;

    public String getName() {
        return name;
    }

    public Double getDefaultPrice() {
        return defaultPrice;
    }

    public Double getMinimumPrice() {
        return minimumPrice;
    }

    public Long getStockAmount() {
        return stockAmount;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
