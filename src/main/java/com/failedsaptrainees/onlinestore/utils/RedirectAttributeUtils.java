package com.failedsaptrainees.onlinestore.utils;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

public class RedirectAttributeUtils {

    public static void addErrorAttribute(RedirectAttributes redirectAttributes, String error)
    {

        List<String> errorList = new ArrayList<>();

        Object errorAttributes = redirectAttributes.getFlashAttributes().get("errors");
        if(errorAttributes != null)
        {
            try{
                errorList = (List<String>) errorAttributes;
            } catch (ClassCastException e)
            {
                System.out.println("Error attempting to cast an error list!");
                System.out.println(e.getStackTrace().toString());
            }
        }

        errorList.add(error);
        redirectAttributes.addFlashAttribute("errors", errorList);
    }


}
