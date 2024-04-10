package com.failedsaptrainees.onlinestore.services;

import com.failedsaptrainees.onlinestore.models.CartProductModel;
import com.failedsaptrainees.onlinestore.models.ProductModel;
import com.failedsaptrainees.onlinestore.repositories.CartProductRepository;
import com.failedsaptrainees.onlinestore.repositories.UserRepository;
import com.failedsaptrainees.onlinestore.utils.AuthenticationChecker;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartProductServiceImpl implements CartProductService {


    @Autowired
    private CartProductRepository cartProductRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<CartProductModel> getCart(HttpSession httpSession) {

        if(AuthenticationChecker.isLoggedIn())
        {
            return getCartFromDB();
        } else
        {
            return getCartFromSession(httpSession);
        }

    }

    @Override
    public List<CartProductModel> getCartFromSession(HttpSession httpSession) {
        List<CartProductModel> list = (List<CartProductModel>) httpSession.getAttribute("cart");
        if(list == null)
        {
            list = new ArrayList<>();
            httpSession.setAttribute("cart", list);
        }
        return list;

    }

    @Override
    public List<CartProductModel> getCartFromDB() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<CartProductModel> list = cartProductRepository.findAllByUser(userService.getUserByEmail(authentication.getName()));

        if(list == null)
        {
            list = new ArrayList<>();
        }

        return list;
    }

    @Override
    public void addItemToCart(List<CartProductModel> cartList, ProductModel productModel) {

        Optional<CartProductModel> cartProduct = cartList.stream().
                filter(cartProductModel -> cartProductModel.getProduct().getId().equals(productModel.getId())).
                findFirst();

        if(cartProduct.isPresent())
        {
            setItemAmount(cartList, productModel, cartProduct.get().getAmount() + 1);
            return;
        }

        CartProductModel newCartProduct = new CartProductModel();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated())
        {
            newCartProduct.setUser(userService.getUserByEmail(authentication.getName()));
        }

        newCartProduct.setProduct(productModel);
        newCartProduct.setAmount(1);
        cartList.add(newCartProduct);
    }


    @Override
    public void addItemToCart(List<CartProductModel> cartList, ProductModel productModel, int amount) {

        Optional<CartProductModel> cartProduct = cartList.stream().
                filter(cartProductModel -> cartProductModel.getProduct().getId().equals(productModel.getId())).
                findFirst();

        if(cartProduct.isPresent())
        {
            setItemAmount(cartList, productModel, cartProduct.get().getAmount() + amount);
            return;
        }

        CartProductModel newCartProduct = new CartProductModel();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated())
        {
            newCartProduct.setUser(userService.getUserByEmail(authentication.getName()));
        }

        newCartProduct.setProduct(productModel);
        newCartProduct.setAmount(amount);
        cartList.add(newCartProduct);
    }


    @Override
    public void removeItemFromCart(List<CartProductModel> cartList, ProductModel productModel) {

        Optional<CartProductModel> cartProduct = cartList.stream().
                filter(cartProductModel -> cartProductModel.getProduct().getId().equals(productModel.getId())).
                findFirst();

        if(cartProduct.isEmpty())
        {
            return;
        }


        if(AuthenticationChecker.isLoggedIn())
        {
            cartProductRepository.delete(cartProduct.get());
        }

        cartList.remove(cartProduct.get());

    }

    @Override
    public void setItemAmount(List<CartProductModel> cartList, ProductModel productModel, int amount) {

        Optional<CartProductModel> cartProduct = cartList.stream().
                filter(cartProductModel -> cartProductModel.getProduct().getId().equals(productModel.getId())).
                findFirst();

        if(cartProduct.isEmpty())
        {
            return;
        }

        if(amount <= 0)
        {
            removeItemFromCart(cartList, productModel);
            return;
        }

        cartProduct.get().setAmount(amount);

    }

    @Override
    public boolean saveCart(List<CartProductModel> cartProductModels, HttpSession httpSession) {

        if(AuthenticationChecker.isLoggedIn())
        {
            for (CartProductModel cartProductModel : cartProductModels) {
                cartProductRepository.save(cartProductModel);
            }

            cartProductRepository.flush();
        } else
        {
            httpSession.setAttribute("cart", cartProductModels);
        }
        return true;
    }

    @Override
    public List<ProductModel> getCartProducts(List<CartProductModel> cartProductModels) {
        return cartProductModels.stream().map(CartProductModel::getProduct).toList();
    }
}
