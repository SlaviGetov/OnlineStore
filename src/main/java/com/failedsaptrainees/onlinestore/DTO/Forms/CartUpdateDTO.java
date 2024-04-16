package com.failedsaptrainees.onlinestore.DTO.Forms;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@NoArgsConstructor
public class CartUpdateDTO {


    private int[] amounts;

    public int[] getAmounts() {
        return amounts;
    }

    public void setAmounts(int[] amounts) {
        this.amounts = amounts;
    }
}
