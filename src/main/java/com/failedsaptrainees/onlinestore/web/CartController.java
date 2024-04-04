package com.failedsaptrainees.onlinestore.web;

import com.failedsaptrainees.onlinestore.models.CartModel;
import com.failedsaptrainees.onlinestore.models.ProductModel;
import com.failedsaptrainees.onlinestore.services.CartService;
import com.failedsaptrainees.onlinestore.services.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public String viewCart(HttpSession httpSession, Model model)
    {
        CartModel cartModel = cartService.getCart(httpSession);
        //TODO: Use the ProductViewDTO.
        model.addAttribute("products", cartModel.products);
        return "cartView";
    }

    @GetMapping("/add/{id}")
    public String addProductToCart(@PathVariable(name = "id") Long product_id, HttpSession httpSession)
    {
        CartModel cart = cartService.getCart(httpSession);
        cartService.addItemToCart(cart, productService.getProductByID(Math.toIntExact(product_id)));
        cartService.saveCart(cart, httpSession);
        return "redirect:/cart/";
    }

    @GetMapping("/remove/{id}")
    public String removeProductFromCart(@PathVariable(name="id") Long product_id, HttpSession httpSession)
    {
        CartModel cart = cartService.getCart(httpSession);
        cartService.removeItemFromCart(cart, productService.getProductByID(Math.toIntExact(product_id)));
        cartService.saveCart(cart, httpSession);
        return "redirect:/cart/";
    }
}
