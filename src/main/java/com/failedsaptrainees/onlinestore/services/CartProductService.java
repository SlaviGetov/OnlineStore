package com.failedsaptrainees.onlinestore.services;

import com.failedsaptrainees.onlinestore.models.CartProductModel;
import com.failedsaptrainees.onlinestore.models.ProductModel;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface CartProductService {

    public List<CartProductModel> getCart(HttpSession httpSession);
    public List<CartProductModel> getCartFromSession(HttpSession httpSession);
    public List<CartProductModel> getCartFromDB();
    public void addItemToCart(List<CartProductModel> cartProductModels, ProductModel productModel);
    public void addItemToCart(List<CartProductModel> cartProductModels, ProductModel productModel, int amount);
    public void removeItemFromCart(List<CartProductModel> cartProductModels, ProductModel productModel);
    public void setItemAmount(List<CartProductModel> cartProductModels, ProductModel productModel, int amount);
    public boolean saveCart(List<CartProductModel> cartProductModels, HttpSession httpSession);
    public void emptyCart(HttpSession httpSession);

    public List<ProductModel> getCartProducts(List<CartProductModel> cartProductModels);

}
