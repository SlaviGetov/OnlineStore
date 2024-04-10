package com.failedsaptrainees.onlinestore.services;

import com.failedsaptrainees.onlinestore.models.DiscountModel;
import com.failedsaptrainees.onlinestore.models.ProductModel;
import com.failedsaptrainees.onlinestore.repositories.DiscountRepository;
import com.failedsaptrainees.onlinestore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Override
    public List<DiscountModel> getAllDiscounts() {
        return discountRepository.findAll();
    }

    @Override
    public List<DiscountModel> getAllDiscounts(boolean isActive) {
        return discountRepository.getDiscountsByIsActive(isActive);
    }

    @Override
    public List<DiscountModel> getDiscountsForProduct(ProductModel product) {
        return discountRepository.getDiscountsByProducts(product);
    }

    @Override
    public List<DiscountModel> getDiscountsForProduct(ProductModel product, boolean isActive) {
        return discountRepository.getDiscountsByProductsAndIsActive(product, isActive);
    }

    @Override
    public void addDiscount(DiscountModel discount) {
        discountRepository.saveAndFlush(discount);
    }


    @Override
    public void setDiscountActive(DiscountModel discountModel, boolean isActive) {
        discountModel.setActive(isActive);
        discountRepository.saveAndFlush(discountModel);
    }
}
