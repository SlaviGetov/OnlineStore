package com.failedsaptrainees.onlinestore.integrationTests;

import com.failedsaptrainees.onlinestore.exceptions.DiscountException;
import com.failedsaptrainees.onlinestore.exceptions.ProductException;
import com.failedsaptrainees.onlinestore.models.CategoryModel;
import com.failedsaptrainees.onlinestore.models.DiscountModel;
import com.failedsaptrainees.onlinestore.models.ProductModel;
import com.failedsaptrainees.onlinestore.repositories.DiscountRepository;
import com.failedsaptrainees.onlinestore.services.DiscountService;
import com.failedsaptrainees.onlinestore.services.DiscountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class DiscountServiceTests {

    @Autowired
    private DiscountServiceImpl discountService;

    @MockBean
    private DiscountRepository discountRepository;

    private List<ProductModel> mockProducts;
    private List<DiscountModel> mockDiscountList;
    private List<DiscountModel> activeDiscounts;
    private List<DiscountModel> inactiveDiscounts;

    @BeforeEach
    public void setup() throws ProductException {

        mockProducts = new ArrayList<>();
        activeDiscounts = new ArrayList<>();
        inactiveDiscounts = new ArrayList<>();
        mockDiscountList = new ArrayList<>();

        mockProducts.add(
                new ProductModel("TestLink", "TestName", 1000D, 100D, 15L, new CategoryModel())
        );
        mockProducts.add(
                new ProductModel("TestLink1", "TestName1", 2000D, 200D, 15L, new CategoryModel())
        );

        mockDiscountList.add(
                new DiscountModel("TestDiscount", true, 0.5, mockProducts)
        );
        mockDiscountList.add(
                new DiscountModel("TestDiscount", false, 0.5, mockProducts)
        );

        activeDiscounts.add(mockDiscountList.get(0));
        inactiveDiscounts.add(mockDiscountList.get(1));

        Mockito.when(discountRepository.getDiscountsByProductsAndIsActive(Mockito.any(), Mockito.eq(true))).thenReturn(activeDiscounts);
        Mockito.when(discountRepository.getDiscountsByProductsAndIsActive(Mockito.any(), Mockito.eq(false))).thenReturn(inactiveDiscounts);
        Mockito.when(discountRepository.getDiscountsByIsActive(Mockito.eq(true))).thenReturn(activeDiscounts);
        Mockito.when(discountRepository.getDiscountsByIsActive(Mockito.eq(false))).thenReturn(inactiveDiscounts);
        Mockito.when(discountRepository.getDiscountsByProducts(Mockito.any())).thenReturn(mockDiscountList);
        Mockito.when(discountRepository.findAll()).thenReturn(mockDiscountList);
    }

    @Test
    public void getAllDiscounts()
    {
        Assertions.assertArrayEquals(mockDiscountList.toArray(), discountService.getAllDiscounts().toArray());
    }

    @Test
    public void getAllActiveDiscounts()
    {
        Assertions.assertArrayEquals(activeDiscounts.toArray(), discountService.getAllDiscounts(true).toArray());
    }

    @Test
    public void getAllInactiveDiscounts()
    {
        Assertions.assertArrayEquals(inactiveDiscounts.toArray(), discountService.getAllDiscounts(false).toArray());
    }

    @Test
    public void getAllDiscountsForProduct()
    {
        Assertions.assertArrayEquals(mockDiscountList.toArray(), discountService.getDiscountsForProduct(mockProducts.get(0)).toArray());
    }

    @Test
    public void getAllActiveDiscountsForProduct()
    {
        Assertions.assertArrayEquals(activeDiscounts.toArray(), discountService.getDiscountsForProduct(mockProducts.get(0), true).toArray());
    }

    @Test
    public void getAllInactiveDiscountsForProduct()
    {
        Assertions.assertArrayEquals(inactiveDiscounts.toArray(), discountService.getDiscountsForProduct(mockProducts.get(0), false).toArray());
    }

    @Test
    public void addDiscount()
    {
        discountService.addDiscount(new DiscountModel("test", true, 0.5, new ArrayList<>()));
        Mockito.verify(discountRepository).saveAndFlush(Mockito.any());
    }

    @Test
    public void getDiscountById_whenDiscountDoesntExist()
    {
        Assertions.assertThrows(DiscountException.class, () -> {
            discountService.getDiscountById(15L);
        });
    }
}
