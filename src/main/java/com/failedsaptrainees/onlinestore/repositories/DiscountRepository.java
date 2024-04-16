package com.failedsaptrainees.onlinestore.repositories;

import com.failedsaptrainees.onlinestore.models.DiscountModel;
import com.failedsaptrainees.onlinestore.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscountRepository extends JpaRepository<DiscountModel, Long> {
    public List<DiscountModel> getDiscountsByProducts(ProductModel productModel);
    public List<DiscountModel> getDiscountsByProductsAndIsActive(ProductModel productModel, boolean isActive);
    public List<DiscountModel> getDiscountsByIsActive(boolean isActive);
}
