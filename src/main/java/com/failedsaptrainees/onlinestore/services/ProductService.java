package com.failedsaptrainees.onlinestore.services;

import com.failedsaptrainees.onlinestore.DTO.Views.ProductViewDTO;
import com.failedsaptrainees.onlinestore.models.ProductModel;

import java.util.List;

public interface ProductService {

    public List<ProductViewDTO> getAllProducts();
    public void deleteProduct(ProductModel productModel);
    public void updateProduct(ProductViewDTO productViewDTO);
    public void insertProduct(ProductModel productModel);
    public ProductModel getProductByID(int id);

}
