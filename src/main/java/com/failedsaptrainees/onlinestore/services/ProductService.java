package com.failedsaptrainees.onlinestore.services;

import com.failedsaptrainees.onlinestore.exceptions.ProductException;
import com.failedsaptrainees.onlinestore.models.CategoryModel;
import com.failedsaptrainees.onlinestore.models.ProductModel;

import java.util.List;

public interface ProductService {

    public List<ProductModel> getAllProducts();
    public void deleteProduct(ProductModel productModel);
    public void updateProduct(Long id, ProductModel productModel);
    public void insertProduct(ProductModel productModel);
    public ProductModel getProductByID(Long id) throws ProductException;
    public Double getProductCurrentPrice(ProductModel productModel);
    public List<ProductModel> getAllProductsByCategory(CategoryModel category);
    public List<ProductModel> getNRandomDiscountedProductsFromCategory(CategoryModel category, int n);
    public List<ProductModel> getNRandomDiscountedProducts(int n);
    public List<ProductModel> findByNameContaining(String name);

}
