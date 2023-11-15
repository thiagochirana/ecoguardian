package br.com.ecoguardian.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private BaseController base;

    @GetMapping
    public ModelAndView getDashboard(){
        ModelAndView model = base.novaView("dashboard");
        return model;
    }

}
