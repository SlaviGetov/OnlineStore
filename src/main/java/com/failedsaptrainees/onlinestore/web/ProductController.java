package com.failedsaptrainees.onlinestore.web;

import com.failedsaptrainees.onlinestore.DTO.Forms.ProductDTO;
import com.failedsaptrainees.onlinestore.DTO.Views.ProductViewDTO;
import com.failedsaptrainees.onlinestore.Validators.ProductDTOValidator;
import com.failedsaptrainees.onlinestore.exceptions.CategoryException;
import com.failedsaptrainees.onlinestore.exceptions.ProductException;
import com.failedsaptrainees.onlinestore.mappers.ProductMapper;
import com.failedsaptrainees.onlinestore.models.CategoryModel;
import com.failedsaptrainees.onlinestore.models.ProductModel;
import com.failedsaptrainees.onlinestore.services.CategoryService;
import com.failedsaptrainees.onlinestore.services.ProductService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
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

    @Autowired
    private ProductMapper productMapper;


    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public String getProducts(Model model) throws CategoryException {
        List<ProductViewDTO> productViewDTOS = new ArrayList<>();

        for (ProductModel product : productService.getAllProducts()) {
            productViewDTOS.add(new ProductViewDTO(product, productService.getProductCurrentPrice(product)));
        }

        model.addAttribute("products", productViewDTOS);
        addDiscountedProductsSideBar(model);


        return "products/employeeViewProducts";
    }

    @GetMapping("{id}")
    public String viewProduct(@PathVariable(name = "id") Long product_id, Model model) throws ProductException {
        ProductModel productModel = productService.getProductByID(Math.toIntExact(product_id));
        ProductViewDTO productViewDTO = new ProductViewDTO(productModel, productService.getProductCurrentPrice(productModel));

        model.addAttribute("product", productViewDTO);
        return "products/product_info";
    }

    @GetMapping("/category/{category_id}")
    public String getProductsByCategory(@PathVariable(name = "category_id") Long categoryId, Model model) throws ChangeSetPersister.NotFoundException {
        try {

            CategoryModel category = categoryService.getCategoryById(categoryId);

            List<ProductViewDTO> productViewDTOS = new ArrayList<>();

            for (ProductModel product : productService.getAllProductsByCategory(category)) {
                productViewDTOS.add(new ProductViewDTO(product, productService.getProductCurrentPrice(product)));
            }

            addDiscountedProductsSideBar(model);

            model.addAttribute("products", productViewDTOS);
            return "products/laptops";
        } catch (CategoryException e) {
            throw new ChangeSetPersister.NotFoundException();
        }
    }

    @GetMapping("/search")
    public String getProductsBySearching(@RequestParam(name = "search") String searchTerm, Model model)
    {
        List<ProductViewDTO> productViewDTOS = new ArrayList<>();

        for (ProductModel product : productService.findByNameContaining(searchTerm)) {
            productViewDTOS.add(new ProductViewDTO(product, productService.getProductCurrentPrice(product)));
        }

        addDiscountedProductsSideBar(model);

        model.addAttribute("products", productViewDTOS);
        return "products/laptops";
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public String addProduct(Model model)
    {
        model.addAttribute("formUrl", "/products/add");
        model.addAttribute("categoryList", categoryService.getAllCategories());
        return "products/productForm";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public String addProductPost(@Valid @ModelAttribute ProductDTO productDTO, BindingResult bindingResult, RedirectAttributes redirectAttrs) throws CategoryException {

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

        ProductModel productModel = productMapper.getProductModelFromProductDTO(productDTO, categoryService.getCategoryByName(productDTO.getCategory()));

        productService.insertProduct(productModel);

        return "redirect:/products";
    }


    @GetMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public String updateProduct(@PathVariable("id") int id, Model model) throws ProductException {

        ModelMapper modelMapper = new ModelMapper();
        ProductModel productModel = productService.getProductByID(id);
        model.addAttribute("formUrl", "/products/update/" + id);
        model.addAttribute("product", new ProductViewDTO(productModel, productService.getProductCurrentPrice(productModel)));
        model.addAttribute("categoryList", categoryService.getAllCategories());

        return "products/productForm";
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public String updateProductPost(@PathVariable("id") Long id, @Valid @ModelAttribute ProductDTO productDTO,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes,
                                    Model model) throws CategoryException {

        productDTOValidator.validate(productDTO, bindingResult);
        if(bindingResult.hasErrors())
        {
            for (ObjectError allError : bindingResult.getAllErrors()) {
                System.out.println(allError.getDefaultMessage());
            }

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("product", productDTO);

            return "redirect:/products/update/"+id;
        }

        ProductModel productModel = productMapper.getProductModelFromProductDTO(productDTO, categoryService.getCategoryByName(productDTO.getCategory()));
        productService.updateProduct(id, productModel);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public String deleteProduct(@PathVariable("id") int id) throws ProductException {

        ProductModel productModel = productService.getProductByID(id);
        productService.deleteProduct(productModel);

        return "redirect:/products/";
    }

    private void addDiscountedProductsSideBar(Model model)
    {

        List<ProductViewDTO> productViewDTOS = new ArrayList<>();

        for (ProductModel product : productService.get4RandomDiscountedProducts()) {
            productViewDTOS.add(new ProductViewDTO(product, productService.getProductCurrentPrice(product)));
        }

        model.addAttribute("discounted_products", productViewDTOS);
    }

}
