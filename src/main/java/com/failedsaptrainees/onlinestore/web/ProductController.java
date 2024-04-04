package com.failedsaptrainees.onlinestore.web;

import com.failedsaptrainees.onlinestore.DTO.ProductViewDTO;
import com.failedsaptrainees.onlinestore.models.ProductModel;
import com.failedsaptrainees.onlinestore.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public ModelAndView getProducts()
    {

        ModelAndView modelAndView = new ModelAndView("productList");
        modelAndView.addObject("products", productService.getAllProducts());

        return modelAndView;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addProduct(Model model)
    {
        model.addAttribute("formUrl", "/products/add");
        return "productForm";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addProductPost(@ModelAttribute ProductViewDTO productViewDTO)
    {
        productViewDTO.setCurrentPrice(productViewDTO.getDefaultPrice());
        productService.insertProduct(productViewDTO);
        return "redirect:/products";
    }


    @GetMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateProduct(@PathVariable("id") int id, Model model)
    {
        model.addAttribute("formUrl", "/products/update/" + id);
        model.addAttribute("product", new ProductViewDTO(productService.getProductByID(id)));

        return "productForm";
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateProductPost(@PathVariable("id") int id, @ModelAttribute ProductViewDTO productViewDTO)
    {
        productViewDTO.setId(Integer.toUnsignedLong(id));
        productService.updateProduct(productViewDTO);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteProduct(@PathVariable("id") int id)
    {

        ProductModel productModel = productService.getProductByID(id);
        productService.deleteProduct(productModel);

        return "redirect:/products/";
    }

}
