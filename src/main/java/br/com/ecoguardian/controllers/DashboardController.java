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
    private ViewBase base;

    @GetMapping
    public ModelAndView getDashboard(){
        ModelAndView model = base.novaView("dashboard/dashboard");
        model.addObject("qtdeDenunciasAbertas",1);
        model.addObject("qtdeDenunciasEmAnalise",4);
        model.addObject("qtdeDenunciasPrecisaAtencao",2);
        return model;
    }

}
