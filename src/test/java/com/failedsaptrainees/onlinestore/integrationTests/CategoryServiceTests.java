package com.failedsaptrainees.onlinestore.integrationTests;

import com.failedsaptrainees.onlinestore.exceptions.CategoryException;
import com.failedsaptrainees.onlinestore.models.CategoryModel;
import com.failedsaptrainees.onlinestore.repositories.CategoryRepository;
import com.failedsaptrainees.onlinestore.services.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CategoryServiceTests {

    @Autowired
    private CategoryServiceImpl categoryService;
    @MockBean
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setup()
    {
        Mockito.when(categoryRepository.findAll()).thenReturn(new ArrayList<>());
    }

    @Test
    public void getCategoryByName_whenNoCategoryExists()
    {
        assertThrows(CategoryException.class, () -> {
           categoryService.getCategoryByName("TestCat1");
        });
    }

    @Test
    public void getCategoryById_whenNoCategoryExists()
    {
        assertThrows(CategoryException.class, () -> {
            categoryService.getCategoryById(5L);
        });
    }

}
