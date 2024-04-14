package com.failedsaptrainees.onlinestore.services;

import com.failedsaptrainees.onlinestore.exceptions.CategoryException;
import com.failedsaptrainees.onlinestore.models.CategoryModel;
import com.failedsaptrainees.onlinestore.models.ProductModel;
import com.failedsaptrainees.onlinestore.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryModel> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryModel getCategoryByName(String categoryName) throws CategoryException {
        Optional<CategoryModel> category = categoryRepository.findCategoryByName(categoryName);
        if(category.isEmpty())
            throw new CategoryException("Category with name " + categoryName + " doesn't exist!");

        return category.get();
    }

    @Override
    public CategoryModel getCategoryById(Long categoryId) throws CategoryException {
        Optional<CategoryModel> category = categoryRepository.findById(categoryId);
        if(category.isEmpty())
            throw new CategoryException("Category with id " + categoryId + " doesn't exist!");

        return category.get();
    }
}
