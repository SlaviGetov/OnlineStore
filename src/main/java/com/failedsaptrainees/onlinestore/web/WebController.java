package com.failedsaptrainees.onlinestore.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("")
public class WebController {

    @RequestMapping("")
    @ResponseBody
    public String index()
    {
        return "Hello World!";
    }

    @RequestMapping("/contact")
    public String contactus()
    {
        return "contact_us";
    }



}
