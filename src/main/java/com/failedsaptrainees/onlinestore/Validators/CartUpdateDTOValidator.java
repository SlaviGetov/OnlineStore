package com.failedsaptrainees.onlinestore.Validators;

import com.failedsaptrainees.onlinestore.DTO.Forms.CartUpdateDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CartUpdateDTOValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return CartUpdateDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        CartUpdateDTO cartUpdateDTO = (CartUpdateDTO) target;

        if(cartUpdateDTO.getAmounts() != null)
        {
            for (int amount : cartUpdateDTO.getAmounts()) {
                if(amount <= 0)
                {
                    errors.rejectValue("amounts", "amountInvalid", "The amount of items needs to be 1 or more.");
                    return;
                }
            }
        }

    }
}
