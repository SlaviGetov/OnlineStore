package com.failedsaptrainees.onlinestore.repositories;

import com.failedsaptrainees.onlinestore.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
}
