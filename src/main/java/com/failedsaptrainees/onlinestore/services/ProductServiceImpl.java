package com.failedsaptrainees.onlinestore.services;

import com.failedsaptrainees.onlinestore.DTO.ProductViewDTO;
import com.failedsaptrainees.onlinestore.models.ProductModel;
import com.failedsaptrainees.onlinestore.repositories.ProductRepository;
import org.hibernate.annotations.NotFound;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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


    //TODO: Throw 404 error if product isn't found.
    //TODO: Check if product is discounted and set the current price as the discounted price.
    @Override
    public void updateProduct(ProductViewDTO productViewDTO)
    {
        ProductModel productModel = productRepository.findById(productViewDTO.getId()).get();

        productModel.setName(productViewDTO.getName());
        productModel.setDefaultPrice(productViewDTO.getDefaultPrice());
        productModel.setCurrentPrice(productViewDTO.getDefaultPrice());
        productModel.setMinimumPrice(productViewDTO.getMinimumPrice());
        productModel.setStockAmount(productViewDTO.getStockAmount());
        productModel.setImageLink(productViewDTO.getImageLink());

        productRepository.save(productModel);
    }

    @Override
    public void insertProduct(ProductViewDTO productViewDTO) {
        ProductModel productModel = new ProductModel();

        productModel.setName(productViewDTO.getName());
        productModel.setDefaultPrice(productViewDTO.getDefaultPrice());
        productModel.setCurrentPrice(productViewDTO.getDefaultPrice());
        productModel.setMinimumPrice(productViewDTO.getMinimumPrice());
        productModel.setStockAmount(productViewDTO.getStockAmount());
        productModel.setImageLink(productViewDTO.getImageLink());

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
