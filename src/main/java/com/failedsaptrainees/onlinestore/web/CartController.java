package com.failedsaptrainees.onlinestore.web;

import com.failedsaptrainees.onlinestore.DTO.Forms.CartUpdateDTO;
import com.failedsaptrainees.onlinestore.DTO.Views.CartViewDTO;
import com.failedsaptrainees.onlinestore.Validators.CartUpdateDTOValidator;
import com.failedsaptrainees.onlinestore.exceptions.ProductException;
import com.failedsaptrainees.onlinestore.models.CartProductModel;
import com.failedsaptrainees.onlinestore.services.CartProductServiceImpl;
import com.failedsaptrainees.onlinestore.services.ProductService;
import com.failedsaptrainees.onlinestore.utils.ErrorAttributeUtils;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartProductServiceImpl cartProductService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartUpdateDTOValidator cartUpdateDTOValidator;


    @GetMapping("")
    public String viewCart(HttpSession httpSession, Model model)
    {
        List<CartProductModel> cartList = cartProductService.getCart(httpSession);
        double totalSum = 0;


        for (CartProductModel cartProduct : new ArrayList<>(cartList)) {
            if(cartProduct.getAmount() > cartProduct.getProduct().getStockAmount())
            {
                ErrorAttributeUtils.addErrorToModel(model, "There aren't " + cartProduct.getAmount() + " of " +
                        cartProduct.getProduct().getName() + ". There's only " + cartProduct.getProduct().getStockAmount() + " in stock right now.");
                cartProductService.setItemAmount(cartList, cartProduct.getProduct(), Math.toIntExact(cartProduct.getProduct().getStockAmount()));
            }
        }

        List<CartViewDTO> cartItemsDTO = new ArrayList<>();
        for (CartProductModel cartProductModel : cartList) {

            double currentPrice = productService.getProductCurrentPrice(cartProductModel.getProduct());

            cartItemsDTO.add(new CartViewDTO(
                    cartProductModel.getProduct().getImageLink(),
                    cartProductModel.getProduct().getId(),
                    cartProductModel.getProduct().getName(),
                    currentPrice,
                    cartProductModel.getAmount()
            ));

            totalSum += currentPrice * cartProductModel.getAmount();
        }

        model.addAttribute("cartProducts", cartItemsDTO);
        model.addAttribute("totalSum", totalSum);
        return "cart/cart";
    }

    @GetMapping("/add/{id}")
    public String addProductToCart(@PathVariable(name = "id") Long product_id, HttpSession httpSession) throws ProductException {
        List<CartProductModel> cartList = cartProductService.getCart(httpSession);
        cartProductService.addItemToCart(cartList, productService.getProductByID(product_id));
        cartProductService.saveCart(cartList, httpSession);
        return "redirect:/cart/";
    }

    @PostMapping("/update")
    public String updateCart(@Valid CartUpdateDTO cartUpdateDTO,
                             BindingResult bindingResult,
                             HttpSession httpSession,
                             RedirectAttributes redirectAttributes)
    {

        cartUpdateDTOValidator.validate(cartUpdateDTO, bindingResult);
        if(!bindingResult.hasErrors())
        {
            List<CartProductModel> cartList = cartProductService.getCart(httpSession);
            if(cartUpdateDTO.getAmounts() != null)
            {
                for (int i = 0; i < cartUpdateDTO.getAmounts().length; i++) {
                    cartList.get(i).setAmount(cartUpdateDTO.getAmounts()[i]);
                }
            }
            cartProductService.saveCart(cartList, httpSession);
        }

        return "redirect:/cart/";
    }


    @GetMapping("/remove/{id}")
    public String removeProductFromCart(@PathVariable(name="id") Long product_id, HttpSession httpSession) throws ProductException {
        List<CartProductModel> cartList = cartProductService.getCart(httpSession);
        cartProductService.removeItemFromCart(cartList, productService.getProductByID(product_id));
        cartProductService.saveCart(cartList, httpSession);
        return "redirect:/cart/";
    }
}
