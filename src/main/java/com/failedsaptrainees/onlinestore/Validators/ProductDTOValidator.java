package com.failedsaptrainees.onlinestore.Validators;

import com.failedsaptrainees.onlinestore.DTO.Forms.ProductDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ProductDTOValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductDTO productDTO = (ProductDTO) target;

        try{
            if(productDTO.getMinimumPrice() > productDTO.getDefaultPrice())
            {
                errors.rejectValue("minimumPrice", "minPriceHigh", "The minimum price needs to be higher or equal to the default price.");
            }
        } catch (NullPointerException e)
        {
            // Will be handled by annotation checks in ProductDTO
        }
    }
}
