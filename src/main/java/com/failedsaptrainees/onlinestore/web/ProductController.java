package com.failedsaptrainees.onlinestore.web;

import com.failedsaptrainees.onlinestore.DTO.Forms.ProductDTO;
import com.failedsaptrainees.onlinestore.DTO.Views.ProductViewDTO;
import com.failedsaptrainees.onlinestore.Validators.ProductDTOValidator;
import com.failedsaptrainees.onlinestore.exceptions.CategoryException;
import com.failedsaptrainees.onlinestore.exceptions.ProductException;
import com.failedsaptrainees.onlinestore.models.ProductModel;
import com.failedsaptrainees.onlinestore.services.CategoryService;
import com.failedsaptrainees.onlinestore.services.ProductService;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductDTOValidator productDTOValidator;


    @InitBinder
    public void initBinder(@NonNull final WebDataBinder binder)
    {
        binder.addValidators(productDTOValidator);
    }


    @GetMapping("")
    public ModelAndView getProducts()
    {

        ModelAndView modelAndView = new ModelAndView("productList");

        List<ProductViewDTO> productViewDTOS = new ArrayList<>();

        for (ProductModel product : productService.getAllProducts()) {
            productViewDTOS.add(new ProductViewDTO(product, productService.getProductCurrentPrice(product)));
        }

        modelAndView.addObject("products", productViewDTOS);

        return modelAndView;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public String addProduct(Model model)
    {
        model.addAttribute("formUrl", "/products/add");
        model.addAttribute("categoryList", categoryService.getAllCategories());
        return "productForm";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public String addProductPost(@Valid @ModelAttribute ProductDTO productDTO, BindingResult bindingResult, RedirectAttributes redirectAttrs) throws CategoryException {
        if(bindingResult.hasErrors())
        {
            for (ObjectError allError : bindingResult.getAllErrors()) {
                System.out.println(allError.getDefaultMessage());
            }

            redirectAttrs.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttrs.addFlashAttribute("product", productDTO);

            return "redirect:/products/add";
        }

        ModelMapper modelMapper = new ModelMapper();
        ProductModel productModel = (ProductModel) modelMapper.map(productDTO, ProductModel.class);
        productModel.setCategory(categoryService.getCategoryByName(productDTO.getCategory()));
        productService.insertProduct(productModel);

        return "redirect:/products";
    }


    @GetMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public String updateProduct(@PathVariable("id") int id, Model model) throws ProductException {

        ModelMapper modelMapper = new ModelMapper();
        model.addAttribute("formUrl", "/products/update/" + id);
        model.addAttribute("product", modelMapper.map(productService.getProductByID(id), ProductViewDTO.class));
        model.addAttribute("categoryList", categoryService.getAllCategories());

        return "productForm";
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public String updateProductPost(@PathVariable("id") Long id, @Valid @ModelAttribute ProductDTO productDTO,
                                    BindingResult bindingResult,
                                    Model model) throws CategoryException {
        if(bindingResult.hasErrors())
        {
            for (ObjectError allError : bindingResult.getAllErrors()) {
                System.out.println(allError.getDefaultMessage());
            }

            model.addAttribute("errors", bindingResult.getAllErrors());

            ModelMapper modelMapper = new ModelMapper();
            ProductViewDTO productViewDTO = modelMapper.map(productDTO, ProductViewDTO.class);
            productViewDTO.setId(id);
            model.addAttribute("formUrl", "/products/update/" + id);

            model.addAttribute("product", productViewDTO);

            return "productForm";
        }

        ModelMapper modelMapper = new ModelMapper();
        ProductModel productModel = modelMapper.map(productDTO, ProductModel.class);
        productModel.setId(id);
        productModel.setCategory(categoryService.getCategoryByName(productDTO.getCategory()));

        productService.updateProduct(productModel);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public String deleteProduct(@PathVariable("id") int id) throws ProductException {

        ProductModel productModel = productService.getProductByID(id);
        productService.deleteProduct(productModel);

        return "redirect:/products/";
    }

}
