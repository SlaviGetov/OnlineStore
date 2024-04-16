package com.failedsaptrainees.onlinestore.repositories;

import com.failedsaptrainees.onlinestore.models.CategoryModel;
import com.failedsaptrainees.onlinestore.models.ProductModel;
import com.failedsaptrainees.onlinestore.services.CategoryService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {

    public List<ProductModel> findAllProductsByCategory(CategoryModel category);

}
