package com.failedsaptrainees.onlinestore.Validators;

import com.failedsaptrainees.onlinestore.DTO.Forms.ProductDTO;
import com.failedsaptrainees.onlinestore.exceptions.CategoryException;
import com.failedsaptrainees.onlinestore.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ProductDTOValidator implements Validator {

    @Autowired
    private CategoryService categoryService;

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductDTO productDTO = (ProductDTO) target;

        try{

            if(!productDTO.getCategory().isEmpty())
            {
                categoryService.getCategoryByName(productDTO.getCategory());
            }

            if(productDTO.getMinimumPrice() > productDTO.getDefaultPrice())
            {
                errors.rejectValue("minimumPrice", "minPriceHigh", "The minimum price needs to be lower than or equal to the default price.");
            }
        } catch (NullPointerException e)
        {
            // Will be handled by annotation checks in ProductDTO
        } catch (CategoryException e) {
            errors.rejectValue("category", "categoryInvalid", "The selected category doesn't exist!");
        }
    }
}
