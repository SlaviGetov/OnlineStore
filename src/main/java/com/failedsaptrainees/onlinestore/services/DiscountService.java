package com.failedsaptrainees.onlinestore.services;

import com.failedsaptrainees.onlinestore.exceptions.DiscountException;
import com.failedsaptrainees.onlinestore.models.DiscountModel;
import com.failedsaptrainees.onlinestore.models.ProductModel;

import java.util.List;

public interface DiscountService {

    public List<DiscountModel> getAllDiscounts();
    public List<DiscountModel> getAllDiscounts(boolean isActive);
    public List<DiscountModel> getDiscountsForProduct(ProductModel product);
    public List<DiscountModel> getDiscountsForProduct(ProductModel product, boolean isActive);
    public void addDiscount(DiscountModel discount);
    public void updateDiscount(Long id, DiscountModel discount);
    public void deleteDiscount(DiscountModel discount);
    public void setDiscountActive(DiscountModel discountModel, boolean isActive);
    public DiscountModel getDiscountById(Long id) throws DiscountException;

}
