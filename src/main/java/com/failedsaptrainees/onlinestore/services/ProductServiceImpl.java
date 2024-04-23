package com.failedsaptrainees.onlinestore.services;

import com.failedsaptrainees.onlinestore.exceptions.ProductException;
import com.failedsaptrainees.onlinestore.models.CategoryModel;
import com.failedsaptrainees.onlinestore.models.DiscountModel;
import com.failedsaptrainees.onlinestore.models.ProductModel;
import com.failedsaptrainees.onlinestore.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DiscountService discountService;

    @Override
    public List<ProductModel> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void updateProduct(Long id, ProductModel productModel)
    {
        productModel.setId(id);
        productRepository.saveAndFlush(productModel);
    }

    @Override
    public void insertProduct(ProductModel productModel) {
        productRepository.saveAndFlush(productModel);
    }

    @Override
    public void deleteProduct(ProductModel productModel) {
        productRepository.delete(productModel);
    }

    @Override
    public ProductModel getProductByID(Long id) throws ProductException {

        return productRepository.findById(id).orElseThrow(() -> (
                new ProductException("The specified product cannot be found!")));
    }

    @Override
    public Double getProductCurrentPrice(ProductModel productModel) {
        List<DiscountModel> discounts = discountService.getDiscountsForProduct(productModel, true);
        double totalPercentageOff = 0;

        if(!discounts.isEmpty())
        {
            totalPercentageOff = 1;
            for (DiscountModel discount : discounts) {
                totalPercentageOff *= discount.getPercentageDiscount();
            }
        }

        double newPrice = productModel.getDefaultPrice() * (1 - totalPercentageOff);
        if(newPrice <= productModel.getMinimumPrice())
        {
            return productModel.getMinimumPrice();
        }

        return newPrice;
    }

    @Override
    public List<ProductModel> getAllProductsByCategory(CategoryModel category) {
        return productRepository.findAllProductsByCategory(category);
    }

    @Override
    public List<ProductModel> getNRandomDiscountedProductsFromCategory(CategoryModel category, int n) {
        return productRepository.getNDiscountedProductsInCategoryRandomly(category, n);
    }

    @Override
    public List<ProductModel> getNRandomDiscountedProducts(int n) {
        return productRepository.getNRandomDiscountedProducts(n);
    }

    @Override
    public List<ProductModel> findByNameContaining(String name) {
        return productRepository.findByNameContaining(name);
    }
}
