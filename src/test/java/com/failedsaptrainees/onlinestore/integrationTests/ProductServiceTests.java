package com.failedsaptrainees.onlinestore.integrationTests;

import com.failedsaptrainees.onlinestore.exceptions.ProductException;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductServiceTests {

    @Autowired
    private ProductServiceImpl productService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private DiscountService discountService;

    private List<ProductModel> productList;

    @BeforeEach
    public void setup()
    {
        productList = new ArrayList<>();
        when(productRepository.findAll()).thenReturn(productList);
        when(productRepository.saveAndFlush(any(ProductModel.class))).then((answer) -> {
            productList.add(answer.getArgument(0));
            return null;
        });
    }

    @Test
    public void insertProduct_inserts_correctly() throws ProductException {
        ProductModel productModel = new ProductModel(
                "testLink",
                "testName",
                1000D,
                100D,
                10L
        );

        productService.insertProduct(productModel);
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
                    10L
            );
        });
    }

    @Test
    public void calculate_productPrice_whenNotDiscounted() throws ProductException {
        ProductModel productModel = new ProductModel(
                "testLink",
                "testName",
                1000D,
                100D,
                10L
        );

        when(discountService.getDiscountsForProduct(productModel, true)).thenReturn(
                Collections.emptyList()
        );

        assertEquals(productModel.getDefaultPrice(), productService.getProductCurrentPrice(productModel));
    }

    @Test
    public void calculate_productPrice_whenDiscounted() throws ProductException {
        ProductModel productModel = new ProductModel(
                "testLink",
                "testName",
                1000D,
                100D,
                10L
        );

        when(discountService.getDiscountsForProduct(productModel, true)).thenReturn(
                Arrays.asList(new DiscountModel("test", true, 0.5, null))
        );

        assertEquals(500D, productService.getProductCurrentPrice(productModel));
    }

    @Test
    public void calculate_productPrice_whenDiscountedUnderMinimum() throws ProductException {
        ProductModel productModel = new ProductModel(
                "testLink",
                "testName",
                1000D,
                100D,
                10L
        );

        when(discountService.getDiscountsForProduct(productModel, true)).thenReturn(
                Arrays.asList(new DiscountModel("test", true, 1, null))
        );

        assertEquals(productModel.getMinimumPrice(), productService.getProductCurrentPrice(productModel));
    }

    @Test
    public void getProductById_whenNoProductExists()
    {
        assertThrows(ProductException.class, () -> {
            productService.getProductByID(10);
        });
    }

}
