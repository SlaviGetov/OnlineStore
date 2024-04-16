package com.failedsaptrainees.onlinestore.integrationTests;

import com.failedsaptrainees.onlinestore.exceptions.ProductException;
import com.failedsaptrainees.onlinestore.models.CategoryModel;
import com.failedsaptrainees.onlinestore.models.DiscountModel;
import com.failedsaptrainees.onlinestore.models.ProductModel;
import com.failedsaptrainees.onlinestore.repositories.ProductRepository;
import com.failedsaptrainees.onlinestore.services.DiscountService;
import com.failedsaptrainees.onlinestore.services.ProductService;
import com.failedsaptrainees.onlinestore.services.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceTests {

    @Autowired
    private ProductServiceImpl productService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private DiscountService discountService;

    private List<ProductModel> productList;
    private ProductModel mockProduct;

    @BeforeEach
    public void setup() throws ProductException {
        mockProduct = new ProductModel(
            "testLink",
            "testName",
            1000D,
            100D,
            10L,
            new CategoryModel()
        );
        productList = new ArrayList<>();
        when(productRepository.findAll()).thenReturn(productList);
        when(productRepository.saveAndFlush(any(ProductModel.class))).then((answer) -> {
            productList.add(answer.getArgument(0));
            return null;
        });
    }

    @Test
    public void insertProduct_inserts_correctly() {


        productService.insertProduct(mockProduct);
        verify(productRepository).saveAndFlush(any());

        ProductModel resultProduct = productList.get(0);
        assertEquals("testLink", resultProduct.getImageLink());
        assertEquals("testName", resultProduct.getName());
        assertEquals(1000D, resultProduct.getDefaultPrice());
        assertEquals(100D, resultProduct.getMinimumPrice());
        assertEquals(10L, resultProduct.getStockAmount());
    }

    @Test
    public void throwError_when_minmimumPrice_isHigher_than_defaultPrice () {
        assertThrows(ProductException.class, () -> {
            ProductModel productModel = new ProductModel(
                    "",
                    "",
                    10D,
                    100D,
                    10L,
                    new CategoryModel()
            );
        });
    }

    @Test
    public void calculate_productPrice_whenNotDiscounted() {

        when(discountService.getDiscountsForProduct(mockProduct, true)).thenReturn(
                Collections.emptyList()
        );

        assertEquals(mockProduct.getDefaultPrice(), productService.getProductCurrentPrice(mockProduct));
    }

    @Test
    public void calculate_productPrice_whenDiscounted()  {

        when(discountService.getDiscountsForProduct(mockProduct, true)).thenReturn(
                Arrays.asList(new DiscountModel("test", true, 0.5, null))
        );

        assertEquals(mockProduct.getDefaultPrice() * 0.5, productService.getProductCurrentPrice(mockProduct));
    }

    @Test
    public void calculate_productPrice_whenDiscountedUnderMinimum()  {


        when(discountService.getDiscountsForProduct(mockProduct, true)).thenReturn(
                Arrays.asList(new DiscountModel("test", true, 1, null))
        );

        assertEquals(mockProduct.getMinimumPrice(), productService.getProductCurrentPrice(mockProduct));
    }

    @Test
    public void getProductById_whenNoProductExists()
    {
        assertThrows(ProductException.class, () -> {
            productService.getProductByID(10);
        });
    }

}
