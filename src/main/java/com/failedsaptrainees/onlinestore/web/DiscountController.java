package com.failedsaptrainees.onlinestore.web;


import com.failedsaptrainees.onlinestore.exceptions.ProductException;
import com.failedsaptrainees.onlinestore.models.DiscountModel;
import com.failedsaptrainees.onlinestore.models.ProductModel;
import com.failedsaptrainees.onlinestore.services.DiscountService;
import com.failedsaptrainees.onlinestore.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/discounts")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private ProductService productService;

    @GetMapping("")
    @ResponseBody
    private String getAllDiscounts()
    {

        List<DiscountModel> discounts =  discountService.getAllDiscounts();

        for (DiscountModel discount : discounts) {
            System.out.println(discount.getPercentageDiscount());
            System.out.println(discount.isActive());
            for (ProductModel product : discount.getProducts()) {
                System.out.println(product.getName());
            }
        }

        return "getAllDiscounts";
    }

    @GetMapping("/product/{id}")
    @ResponseBody
    private String getAllDiscountsForProduct(@PathVariable(name = "id") int id) throws ProductException {
        ProductModel productModel =  productService.getProductByID(id);
        List<DiscountModel> discounts = discountService.getDiscountsForProduct(productModel, true);
        for (DiscountModel discount : discounts) {
            System.out.println(discount.getPercentageDiscount());
            System.out.println(discount.isActive());
            for (ProductModel product : discount.getProducts()) {
                System.out.println(product.getName());
            }
        }

        return "getAllDiscountsForProduct";
    }

}
