package com.failedsaptrainees.onlinestore.web;


import com.failedsaptrainees.onlinestore.DTO.Forms.IncomeForDurationFormDTO;
import com.failedsaptrainees.onlinestore.services.StatisticsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public String getIncomeForDuration()
    {
        return "statistics/getIncomeForDuration";
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public String getIncomeForDuration(@Valid @ModelAttribute IncomeForDurationFormDTO incomeForDurationFormDTO, BindingResult bindingResult, Model model)
    {
        model.addAttribute("incomeForDurationFormDTO", incomeForDurationFormDTO);


        for (ObjectError allError : bindingResult.getAllErrors()) {
            System.out.println(allError.getDefaultMessage());
        }

        if(!bindingResult.hasErrors())
        {
            Double income = statisticsService.getIncomeBetweenTwoDates(incomeForDurationFormDTO.getFromDate(), incomeForDurationFormDTO.getToDate());
            model.addAttribute("income", income);
        }

        return "statistics/getIncomeForDuration";
    }
}
