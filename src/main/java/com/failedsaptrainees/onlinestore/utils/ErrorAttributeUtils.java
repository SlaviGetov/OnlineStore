package com.failedsaptrainees.onlinestore.utils;

import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

public class ErrorAttributeUtils {

    public static void addErrorToModel(Model model, String error)
    {
        if(model == null)
            throw new NullPointerException("Model was set to null");

        List<String> errorList = new ArrayList<>();

        Object errorAttributes = model.getAttribute("errors");
        if(errorAttributes != null)
        {
            try{
                errorList = (List<String>) errorAttributes;
            } catch (ClassCastException e)
            {
                System.out.println("Error attempting to cast an error list!");
                e.printStackTrace();
            }
        }

        errorList.add(error);
        model.addAttribute("errors", errorList);
    }
}
