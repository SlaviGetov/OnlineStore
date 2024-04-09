package com.failedsaptrainees.onlinestore.web;

import com.failedsaptrainees.onlinestore.DTO.Forms.ProductDTO;
import com.failedsaptrainees.onlinestore.DTO.Views.ProductViewDTO;
import com.failedsaptrainees.onlinestore.Validators.ProductDTOValidator;
import com.failedsaptrainees.onlinestore.models.ProductModel;
import com.failedsaptrainees.onlinestore.services.ProductService;
import jakarta.validation.Valid;
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

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDTOValidator productDTOValidator;


    @GetMapping("")
    public ModelAndView getProducts()
    {

        ModelAndView modelAndView = new ModelAndView("productList");
        modelAndView.addObject("products", productService.getAllProducts());

        return modelAndView;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public String addProduct(Model model)
    {
        model.addAttribute("formUrl", "/products/add");
        return "productForm";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public String addProductPost(@Valid @ModelAttribute ProductDTO productDTO, BindingResult bindingResult, RedirectAttributes redirectAttrs)
    {
        productDTOValidator.validate(productDTO, bindingResult);

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
        productService.insertProduct(productModel);

        return "redirect:/products";
    }


    @GetMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public String updateProduct(@PathVariable("id") int id, Model model)
    {
        model.addAttribute("formUrl", "/products/update/" + id);
        model.addAttribute("product", new ProductViewDTO(productService.getProductByID(id)));

        return "productForm";
    }

    //TODO: Send the made changes over to the view
    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public String updateProductPost(@PathVariable("id") Long id, @Valid @ModelAttribute ProductDTO productDTO,
                                    BindingResult bindingResult,
                                    Model model)
    {

        productDTOValidator.validate(productDTO, bindingResult);

        if(bindingResult.hasErrors())
        {
            for (ObjectError allError : bindingResult.getAllErrors()) {
                System.out.println(allError.getDefaultMessage());
            }

            model.addAttribute("errors", bindingResult.getAllErrors());

            return "redirect:/products/update/"+id;
        }

        ModelMapper modelMapper = new ModelMapper();
        ProductModel productModel = modelMapper.map(productDTO, ProductModel.class);
        productModel.setId(id);

        productService.updateProduct(productModel);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public String deleteProduct(@PathVariable("id") int id)
    {

        ProductModel productModel = productService.getProductByID(id);
        productService.deleteProduct(productModel);

        return "redirect:/products/";
    }

}
