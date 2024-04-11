package com.failedsaptrainees.onlinestore.services;

import com.failedsaptrainees.onlinestore.exceptions.ProductException;
import com.failedsaptrainees.onlinestore.models.CategoryModel;
import com.failedsaptrainees.onlinestore.models.ProductModel;

import java.util.List;

public interface ProductService {

    public List<ProductModel> getAllProducts();
    public void deleteProduct(ProductModel productModel);
    public void updateProduct(ProductModel productModel);
    public void insertProduct(ProductModel productModel);
    public ProductModel getProductByID(int id) throws ProductException;
    public Double getProductCurrentPrice(ProductModel productModel);
    public List<ProductModel> getAllProductsByCategory(CategoryModel category);

}
