package com.failedsaptrainees.onlinestore.services;

import com.failedsaptrainees.onlinestore.models.CartModel;
import com.failedsaptrainees.onlinestore.models.ProductModel;
import jakarta.servlet.http.HttpSession;

public interface CartService {

    public CartModel getCart(HttpSession httpSession);
    public CartModel getCartFromSession(HttpSession httpSession);
    public CartModel getCartFromDB();
    public void addItemToCart(CartModel cart, ProductModel productModel);
    public void removeItemFromCart(CartModel cart, ProductModel productModel);
    public boolean saveCart(CartModel cartModel, HttpSession httpSession);

}
