package com.failedsaptrainees.onlinestore.repositories;

import com.failedsaptrainees.onlinestore.models.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {

}
