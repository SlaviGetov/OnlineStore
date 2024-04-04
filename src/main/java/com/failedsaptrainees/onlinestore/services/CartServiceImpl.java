package com.failedsaptrainees.onlinestore.services;

import com.failedsaptrainees.onlinestore.models.CartModel;
import com.failedsaptrainees.onlinestore.models.ProductModel;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {


    //TODO: Get the cart from the DB if the user is logged in. Differentiate between logged in and non-logged in users.
    @Override
    public CartModel getCart(HttpSession httpSession) {
        return getCartFromSession(httpSession);
    }

    @Override
    public CartModel getCartFromSession(HttpSession httpSession) {
        CartModel cartModel = (CartModel) httpSession.getAttribute("cart");
        if(cartModel == null)
        {
            cartModel = new CartModel();
            httpSession.setAttribute("cart", cartModel);
        }
        return cartModel;
    }

    @Override
    public CartModel getCartFromDB() {
        return null;
    }


    @Override
    public void addItemToCart(CartModel cart, ProductModel productModel) {
        cart.products.add(productModel);
    }

    @Override
    public void removeItemFromCart(CartModel cart, ProductModel productModel) {
        ProductModel productToRemove = cart.products.stream().filter(productModel1 -> productModel1.getId() == productModel.getId())
                .findFirst()
                .get();

        cart.products.remove(productToRemove);
    }


    //TODO: Save the cart directly to the DB if the user is logged in. Differentiate between logged in and non-logged in users.
    @Override
    public boolean saveCart(CartModel cartModel, HttpSession httpSession) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated())
        {
            return false;
        } else
        {
            httpSession.setAttribute("cart", cartModel);
        }

        return true;
    }
}
