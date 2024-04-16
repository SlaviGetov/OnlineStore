package com.failedsaptrainees.onlinestore.services;

import com.failedsaptrainees.onlinestore.exceptions.CategoryException;
import com.failedsaptrainees.onlinestore.models.CategoryModel;
import com.failedsaptrainees.onlinestore.models.ProductModel;

import java.util.List;

public interface CategoryService {

    public List<CategoryModel> getAllCategories();
    public CategoryModel getCategoryByName(String categoryName) throws CategoryException;
    public CategoryModel getCategoryById(Long categoryId) throws CategoryException;
}
