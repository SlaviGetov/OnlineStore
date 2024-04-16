package com.failedsaptrainees.onlinestore.web;


import com.failedsaptrainees.onlinestore.DTO.Forms.DiscountDTO;
import com.failedsaptrainees.onlinestore.DTO.Views.ProductViewDTO;
import com.failedsaptrainees.onlinestore.exceptions.DiscountException;
import com.failedsaptrainees.onlinestore.exceptions.ProductException;
import com.failedsaptrainees.onlinestore.models.DiscountModel;
import com.failedsaptrainees.onlinestore.models.ProductModel;
import com.failedsaptrainees.onlinestore.services.DiscountService;
import com.failedsaptrainees.onlinestore.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/discounts")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private ProductService productService;

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public String getAllDiscounts(Model model)
    {
        List<DiscountModel> discounts =  discountService.getAllDiscounts();
        model.addAttribute("discounts", discounts);
        return "discounts/discountList";
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public String addDiscount(DiscountDTO discountDTO)
    {
        return "discounts/createDiscount";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public String addDiscount(@Valid @ModelAttribute DiscountDTO discountDTO, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "redirect:/discounts/add";
        }

        ModelMapper modelMapper = new ModelMapper();
        DiscountModel discountModel = modelMapper.map(discountDTO, DiscountModel.class);
        discountService.addDiscount(discountModel);

        return "redirect:/discounts/edit/" + discountModel.getId();
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public String editDiscount(@PathVariable(name = "id") Long discountId, Model model)  {

        ModelMapper modelMapper = new ModelMapper();
        try{

        DiscountModel discountModel = discountService.getDiscountById(discountId);
        model.addAttribute("discountDTO", modelMapper.map(discountModel, DiscountDTO.class));
        List<ProductViewDTO> productViewDTOS = new ArrayList<>();
        for (ProductModel product : discountModel.getProducts()) {
            ProductViewDTO productViewDTO = modelMapper.map(product, ProductViewDTO.class);
            productViewDTO.setCurrentPrice(productService.getProductCurrentPrice(product));
            productViewDTOS.add(productViewDTO);
        }
        model.addAttribute("products", productViewDTOS);
        model.addAttribute("discountId", discountId);

        return "discounts/editDiscount";
        } catch (DiscountException e)
        {
            return "discounts/";
        }
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public String editDiscount(@PathVariable(name = "id") Long discountId, @Valid @ModelAttribute DiscountDTO discountDTO, BindingResult bindingResult) throws DiscountException {
        if(bindingResult.hasErrors())
        {
            return "redirect:/discounts/edit/" + discountId;
        }

        DiscountModel discountModel = discountService.getDiscountById(discountId);
        discountModel.setDiscountName(discountDTO.getName());
        discountModel.setPercentageDiscount(discountDTO.getPercentageDiscount());
        discountModel.setActive(discountDTO.isActive());

        discountService.updateDiscount(discountId, discountModel);

        return "redirect:/discounts/edit/" + discountId;
    }

    @PostMapping("/addproduct/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public String addProductToDiscount(@PathVariable(name = "id") Long discountId, HttpServletRequest request)
    {

        try
        {
            int itemId = Integer.parseInt(request.getParameter("itemId"));
            ProductModel productModel = productService.getProductByID(itemId);
            DiscountModel discountModel = discountService.getDiscountById(discountId);
            if(!discountModel.getProducts().contains(productModel))
            {
                discountModel.getProducts().add(productModel);
                discountService.updateDiscount(discountId, discountModel);
            }
        } catch (NullPointerException | NumberFormatException | DiscountException | ProductException e)
        {
            return "redirect:/discounts/edit/" + discountId;
        }

        return "redirect:/discounts/edit/" + discountId;
    }

    @GetMapping("/removeproduct/{id}/{productId}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public String removeProductFromDiscount(@PathVariable(name = "id") Long discountId, @PathVariable(name = "productId") int productId)
    {

        try {
            DiscountModel discountModel = discountService.getDiscountById(discountId);
            ProductModel productModel = productService.getProductByID(productId);
            discountModel.getProducts().remove(productModel);
            discountService.updateDiscount(discountId, discountModel);
        } catch (DiscountException | ProductException e) {
            return "redirect:/discounts/edit/" + discountId;
        }

        return "redirect:/discounts/edit/" + discountId;
    }

}
