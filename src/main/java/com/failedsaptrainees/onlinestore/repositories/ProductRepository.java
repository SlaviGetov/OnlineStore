package com.failedsaptrainees.onlinestore.repositories;

import com.failedsaptrainees.onlinestore.models.CategoryModel;
import com.failedsaptrainees.onlinestore.models.ProductModel;
import com.failedsaptrainees.onlinestore.services.CategoryService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {

    public List<ProductModel> findAllProductsByCategory(CategoryModel category);

    @Query(value = "SELECT p FROM products p " +
            "WHERE category = :category " +
            "AND p IN " +
            "(SELECT products FROM discounts WHERE isActive = true) ORDER BY RAND() LIMIT 4")
    public List<ProductModel> get4DiscountedProductsInCategoryRandomly(CategoryModel category);

    @Query(value = "SELECT p FROM products p " +
            "WHERE p IN " +
            "(SELECT products FROM discounts WHERE isActive = true) ORDER BY RAND() LIMIT 4")
    public List<ProductModel> get4RandomDiscountedProducts();

    public List<ProductModel> findByNameContaining(String name);

}
