package br.com.ecoguardian.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/minhaConta")
public class ContaController {

    @Autowired
    private ViewBase view;

    @GetMapping
    public ModelAndView minhaConta(){
        ModelAndView model = view.novaView("contas/minhaConta");
        return model;
    }
}
