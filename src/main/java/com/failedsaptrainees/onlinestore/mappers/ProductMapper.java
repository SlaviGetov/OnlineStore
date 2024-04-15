package com.failedsaptrainees.onlinestore.mappers;

import com.failedsaptrainees.onlinestore.DTO.Forms.ProductDTO;
import com.failedsaptrainees.onlinestore.DTO.Views.ProductViewDTO;
import com.failedsaptrainees.onlinestore.exceptions.CategoryException;
import com.failedsaptrainees.onlinestore.models.CategoryModel;
import com.failedsaptrainees.onlinestore.models.ProductModel;
import com.failedsaptrainees.onlinestore.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    ModelMapper modelMapper = new ModelMapper();

    public ProductModel getProductModelFromProductDTO(ProductDTO productDTO, CategoryModel categoryModel)  {
        ProductModel productModel = modelMapper.map(productDTO, ProductModel.class);
        productModel.setCategory(categoryModel);
        return productModel;
    }





}
