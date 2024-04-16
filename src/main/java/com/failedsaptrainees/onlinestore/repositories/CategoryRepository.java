package com.failedsaptrainees.onlinestore.repositories;

import com.failedsaptrainees.onlinestore.models.CategoryModel;
import jdk.jfr.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {

    public Optional<CategoryModel> findCategoryByName(String categoryName);

}
