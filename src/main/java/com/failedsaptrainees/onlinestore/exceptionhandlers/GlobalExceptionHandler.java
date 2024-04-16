package com.failedsaptrainees.onlinestore.exceptionhandlers;

import com.failedsaptrainees.onlinestore.exceptions.ProductException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductException.class)
    @ResponseBody
    public String handleError(HttpServletRequest request, Exception exception)
    {
        return exception.getMessage();
    }


}
