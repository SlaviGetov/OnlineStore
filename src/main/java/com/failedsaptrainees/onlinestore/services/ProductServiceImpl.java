package com.failedsaptrainees.onlinestore.services;

import com.failedsaptrainees.onlinestore.DTO.Views.ProductViewDTO;
import com.failedsaptrainees.onlinestore.models.ProductModel;
import com.failedsaptrainees.onlinestore.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductViewDTO> getAllProducts() {

        List<ProductViewDTO> products = new ArrayList<>();
        List<ProductModel> productModels = productRepository.findAll();
        for (ProductModel productModel : productModels) {
            products.add(productModel.getProductViewDTO());
        }

        return products;
    }


    @Override
    public void updateProduct(ProductModel productModel)
    {
        productRepository.save(productModel);
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
    public ProductModel getProductByID(int id) {
        return productRepository.findById(Integer.toUnsignedLong(id)).get();
    }
}
