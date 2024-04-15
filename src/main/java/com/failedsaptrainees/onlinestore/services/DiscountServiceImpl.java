package com.failedsaptrainees.onlinestore.services;

import com.failedsaptrainees.onlinestore.exceptions.DiscountException;
import com.failedsaptrainees.onlinestore.models.DiscountModel;
import com.failedsaptrainees.onlinestore.models.ProductModel;
import com.failedsaptrainees.onlinestore.repositories.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public void updateDiscount(Long id, DiscountModel discount) {
        discount.setId(id);
        discountRepository.saveAndFlush(discount);
    }

    @Override
    public DiscountModel getDiscountById(Long id) throws DiscountException {
        Optional<DiscountModel> discountModel = discountRepository.findById(id);
        if(discountModel.isEmpty())
            throw new DiscountException("Discount not found!");

        return discountModel.get();
    }
}
