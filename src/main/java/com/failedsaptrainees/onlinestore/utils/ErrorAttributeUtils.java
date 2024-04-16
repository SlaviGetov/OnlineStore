package com.failedsaptrainees.onlinestore.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorAttributeUtils {

    public static void addErrorAttribute(Model model, String error)
    {

        List<String> errorList = new ArrayList<>();

        Object errorAttributes = model.getAttribute("errors");
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
        model.addAttribute("errors", errorList);
    }
}
