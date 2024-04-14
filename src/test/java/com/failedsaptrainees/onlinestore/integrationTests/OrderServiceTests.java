package com.failedsaptrainees.onlinestore.integrationTests;

import com.failedsaptrainees.onlinestore.enums.Roles;
import com.failedsaptrainees.onlinestore.exceptions.OrderException;
import com.failedsaptrainees.onlinestore.exceptions.ProductException;
import com.failedsaptrainees.onlinestore.models.*;
import com.failedsaptrainees.onlinestore.repositories.OrderProductRepository;
import com.failedsaptrainees.onlinestore.repositories.OrderRepository;
import com.failedsaptrainees.onlinestore.services.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class OrderServiceTests {

    @Autowired
    private OrderServiceImpl orderService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private OrderProductRepository orderProductRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private ProductService productService;

    private UserModel mockUser;
    private List<OrderModel> mockOrders;
    private List<OrderProductModel> mockOrderProducts;
    private List<ProductModel> mockProducts;

    @BeforeEach
    public void setup() throws ProductException {
        mockUser = new UserModel(
                "test",
                "test",
                "test",
                "123",
                new Date(11, 04, 2003),
                "male",
                new RoleModel("ROLE_TEST")
                );
        mockOrders = new ArrayList<>();
        mockOrderProducts = new ArrayList<>();
        mockProducts = new ArrayList<>();

        mockProducts.add(
                new ProductModel("test1", "test1", 1000D, 100D, 15L, new CategoryModel())
        );

        mockProducts.add(
                new ProductModel("test2", "test2", 1000D, 100D, 1L, new CategoryModel())
        );


        Mockito.when(userService.getUserByEmail(Mockito.any())).thenReturn(mockUser);

        Mockito.when(orderRepository.save(Mockito.any())).then((answer) -> {
            OrderModel orderModel = answer.getArgument(0);
            mockOrders.add(orderModel);
            return orderModel;
        });

        Mockito.when(orderProductRepository.save(Mockito.any())).then((answer) -> {
            OrderProductModel orderProductModel = answer.getArgument(0);
            mockOrderProducts.add(orderProductModel);
            return orderProductModel;
        });
    }

    @Test
    @WithMockUser(username = "test")
    public void sendOrder() throws OrderException {

        Mockito.when(productService.getProductCurrentPrice(Mockito.any())).then((answer) -> {
            ProductModel productModel = answer.getArgument(0);
            return productModel.getDefaultPrice();
        });

        List<CartProductModel> cartProductModels = new ArrayList<>();
        CartProductModel cartProductModel;
        cartProductModel = new CartProductModel();
        cartProductModel.setUser(userService.getUserByEmail("test"));
        cartProductModel.setAmount(2);
        cartProductModel.setProduct(mockProducts.get(0));
        cartProductModels.add(cartProductModel);

        cartProductModel = new CartProductModel();
        cartProductModel.setUser(userService.getUserByEmail("test"));
        cartProductModel.setAmount(1);
        cartProductModel.setProduct(mockProducts.get(1));
        cartProductModels.add(cartProductModel);

        orderService.sendOrder(cartProductModels);

        Assertions.assertEquals(mockUser, mockOrders.get(0).getUserModel());
        Assertions.assertEquals(2, mockOrderProducts.size());
        Assertions.assertEquals(cartProductModels.get(0).getAmount(), mockOrderProducts.get(0).getAmount());
        Assertions.assertEquals(cartProductModels.get(0).getProduct().getDefaultPrice(), mockOrderProducts.get(0).getPriceAtTime());

        Assertions.assertEquals(cartProductModels.get(1).getAmount(), mockOrderProducts.get(1).getAmount());
        Assertions.assertEquals(cartProductModels.get(1).getProduct().getDefaultPrice(), mockOrderProducts.get(1).getPriceAtTime());

    }

    @Test
    @WithMockUser(username = "test")
    public void sendOrder_whenDiscounted() throws OrderException {

        Mockito.when(productService.getProductCurrentPrice(Mockito.any())).then((answer) -> {
            ProductModel productModel = answer.getArgument(0);
            return productModel.getDefaultPrice() / 2;
        });

        List<CartProductModel> cartProductModels = new ArrayList<>();
        CartProductModel cartProductModel;
        cartProductModel = new CartProductModel();
        cartProductModel.setUser(userService.getUserByEmail("test"));
        cartProductModel.setAmount(2);
        cartProductModel.setProduct(mockProducts.get(0));
        cartProductModels.add(cartProductModel);

        cartProductModel = new CartProductModel();
        cartProductModel.setUser(userService.getUserByEmail("test"));
        cartProductModel.setAmount(1);
        cartProductModel.setProduct(mockProducts.get(1));
        cartProductModels.add(cartProductModel);

        orderService.sendOrder(cartProductModels);

        Assertions.assertEquals(mockUser, mockOrders.get(0).getUserModel());
        Assertions.assertEquals(2, mockOrderProducts.size());
        Assertions.assertEquals(cartProductModels.get(0).getAmount(), mockOrderProducts.get(0).getAmount());
        Assertions.assertEquals(cartProductModels.get(0).getProduct().getDefaultPrice() / 2, mockOrderProducts.get(0).getPriceAtTime());

        Assertions.assertEquals(cartProductModels.get(1).getAmount(), mockOrderProducts.get(1).getAmount());
        Assertions.assertEquals(cartProductModels.get(1).getProduct().getDefaultPrice() / 2, mockOrderProducts.get(1).getPriceAtTime());

    }

    @Test
    @WithMockUser(username = "test")
    public void sendOrder_whenItemOutOfStock() {
        Assertions.assertThrows(OrderException.class, () -> {
            Mockito.when(productService.getProductCurrentPrice(Mockito.any())).then((answer) -> {
                ProductModel productModel = answer.getArgument(0);
                return productModel.getDefaultPrice() / 2;
            });

            List<CartProductModel> cartProductModels = new ArrayList<>();
            CartProductModel cartProductModel;
            cartProductModel = new CartProductModel();
            cartProductModel.setUser(userService.getUserByEmail("test"));
            cartProductModel.setAmount(55);
            cartProductModel.setProduct(mockProducts.get(0));
            cartProductModels.add(cartProductModel);

            cartProductModel = new CartProductModel();
            cartProductModel.setUser(userService.getUserByEmail("test"));
            cartProductModel.setAmount(1);
            cartProductModel.setProduct(mockProducts.get(1));
            cartProductModels.add(cartProductModel);

            orderService.sendOrder(cartProductModels);
        });
    }
}
