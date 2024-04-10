package com.failedsaptrainees.onlinestore.web;

import com.failedsaptrainees.onlinestore.DTO.Views.CartViewDTO;
import com.failedsaptrainees.onlinestore.exceptions.ProductException;
import com.failedsaptrainees.onlinestore.models.CartProductModel;
import com.failedsaptrainees.onlinestore.services.CartProductServiceImpl;
import com.failedsaptrainees.onlinestore.services.ProductService;
import com.failedsaptrainees.onlinestore.utils.AuthenticationChecker;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartProductServiceImpl cartProductService;

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public String viewCart(HttpSession httpSession, Model model)
    {
        List<CartProductModel> cartList = cartProductService.getCart(httpSession);

        for (CartProductModel cartProduct : new ArrayList<>(cartList)) {
            if(cartProduct.getAmount() > cartProduct.getProduct().getStockAmount())
            {
                cartProductService.setItemAmount(cartList, cartProduct.getProduct(), Math.toIntExact(cartProduct.getProduct().getStockAmount()));
            }
        }

        List<CartViewDTO> cartItemsDTO = new ArrayList<>();
        for (CartProductModel cartProductModel : cartList) {
            cartItemsDTO.add(new CartViewDTO(
                    cartProductModel.getProduct().getImageLink(),
                    cartProductModel.getProduct().getId(),
                    cartProductModel.getProduct().getName(),
                    productService.getProductCurrentPrice(cartProductModel.getProduct()),
                    cartProductModel.getAmount()
            ));
        }

        model.addAttribute("cartProducts", cartItemsDTO);
        return "cart";
    }

    @GetMapping("/add/{id}")
    public String addProductToCart(@PathVariable(name = "id") Long product_id, HttpSession httpSession) throws ProductException {
        List<CartProductModel> cartList = cartProductService.getCart(httpSession);
        cartProductService.addItemToCart(cartList, productService.getProductByID(Math.toIntExact(product_id)));
        cartProductService.saveCart(cartList, httpSession);
        return "redirect:/cart/";
    }

    @GetMapping("/remove/{id}")
    public String removeProductFromCart(@PathVariable(name="id") Long product_id, HttpSession httpSession) throws ProductException {
        List<CartProductModel> cartList = cartProductService.getCart(httpSession);
        cartProductService.removeItemFromCart(cartList, productService.getProductByID(Math.toIntExact(product_id)));
        cartProductService.saveCart(cartList, httpSession);
        return "redirect:/cart/";
    }
}
