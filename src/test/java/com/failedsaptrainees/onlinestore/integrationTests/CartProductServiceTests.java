package com.failedsaptrainees.onlinestore.integrationTests;

import com.failedsaptrainees.onlinestore.enums.Roles;
import com.failedsaptrainees.onlinestore.exceptions.ProductException;
import com.failedsaptrainees.onlinestore.models.CartProductModel;
import com.failedsaptrainees.onlinestore.models.ProductModel;
import com.failedsaptrainees.onlinestore.models.UserModel;
import com.failedsaptrainees.onlinestore.repositories.CartProductRepository;
import com.failedsaptrainees.onlinestore.repositories.UserRepository;
import com.failedsaptrainees.onlinestore.services.CartProductServiceImpl;
import com.failedsaptrainees.onlinestore.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartProductServiceTests {

    @Autowired
    private CartProductServiceImpl cartProductService;

    @MockBean
    private CartProductRepository cartProductRepository;

    @MockBean
    private UserServiceImpl userService;

    @Mock
    MockHttpSession mockHttpSession;

    private UserModel mockUser;
    private ProductModel mockProduct;

    private List<CartProductModel> mockCartList;

    @BeforeEach
    void setUp() throws ProductException {

        mockHttpSession =new MockHttpSession();
        mockUser = new UserModel(
                "test",
                "test",
                "test",
                "123",
                new Date(11, 11, 2011),
                "male",
                null
        );
        mockProduct = new ProductModel(
                "testLink",
                "testName",
                1000D,
                100D,
                10L
        );
        mockProduct.setId(1L);
        mockCartList = new ArrayList<>();

        Mockito.when(userService.getUserByEmail("test")).thenReturn(mockUser);
        Mockito.when(cartProductRepository.save(Mockito.any(CartProductModel.class))).then((answer) -> {
            CartProductModel cartProductModel = answer.getArgument(0);
            if(mockCartList.contains(cartProductModel))
            {
                int index = mockCartList.indexOf(mockProduct);
                mockCartList.set(index, cartProductModel);
            } else
            {
                mockCartList.add(cartProductModel);
            }
            return cartProductModel;
        });

        CartProductModel cartProductModel = new CartProductModel();
    }


    @Test
    @WithAnonymousUser
    public void addItemToCart_whenNotLoggedIn() {

        List<CartProductModel> cartItems = cartProductService.getCart(mockHttpSession);


        cartProductService.addItemToCart(cartItems, mockProduct);
        cartProductService.saveCart(cartItems, mockHttpSession);
        assertEquals(cartProductService.getCart(mockHttpSession).stream().findFirst().get().getProduct(), mockProduct);
        Mockito.verifyNoInteractions(cartProductRepository);
        Mockito.doAnswer(invocationOnMock -> {
            mockCartList.remove(invocationOnMock.getArgument(0));
            return null;
        }).when(cartProductRepository).delete(Mockito.any());

    }

    @Test
    @WithMockUser(username = "test")
    public void addItemToCart_whenLoggedIn()  {

        List<CartProductModel> cartItems = cartProductService.getCart(mockHttpSession);
        cartProductService.addItemToCart(cartItems, mockProduct);
        cartProductService.saveCart(cartItems, mockHttpSession);
        Mockito.verify(cartProductRepository).save(Mockito.any());
        assertEquals(mockCartList.get(0).getUser(), mockUser);
        assertEquals(mockCartList.get(0).getProduct(), mockProduct);
    }

    @Test
    @WithAnonymousUser
    public void setItemAmount_deleteWhenZeroWhenNotLoggedIn()
    {
        cartProductService.addItemToCart(cartProductService.getCart(mockHttpSession), mockProduct);
        cartProductService.saveCart(cartProductService.getCart(mockHttpSession), mockHttpSession);
        cartProductService.setItemAmount(cartProductService.getCart(mockHttpSession), mockProduct, 0);
        assertEquals(0, cartProductService.getCart(mockHttpSession).size());
    }

    @Test
    @WithMockUser(username = "test")
    public void setItemAmount_deleteWhenZeroWhenLoggedIn()
    {
        cartProductService.addItemToCart(cartProductService.getCart(mockHttpSession), mockProduct);
        cartProductService.saveCart(cartProductService.getCart(mockHttpSession), mockHttpSession);
        cartProductService.setItemAmount(cartProductService.getCart(mockHttpSession), mockProduct, 0);
        assertEquals(0, mockCartList.size());
    }

    @Test
    @WithAnonymousUser
    public void setItemAmount_WhenNotLoggedIn()
    {
        cartProductService.addItemToCart(cartProductService.getCart(mockHttpSession), mockProduct);
        cartProductService.saveCart(cartProductService.getCart(mockHttpSession), mockHttpSession);
        cartProductService.setItemAmount(cartProductService.getCart(mockHttpSession), mockProduct, 5);
        assertEquals(5, cartProductService.getCart(mockHttpSession).stream().findFirst().get().getAmount());
    }


    @Test
    @WithMockUser(username = "test")
    public void setItemAmount_WhenLoggedIn()
    {
        List<CartProductModel> cartItems = cartProductService.getCart(mockHttpSession);
        cartProductService.addItemToCart(cartItems, mockProduct);
        cartProductService.saveCart(cartItems, mockHttpSession);
        cartProductService.setItemAmount(cartItems, mockProduct, 5);
        assertEquals(5, mockCartList.get(0).getAmount());
    }


}