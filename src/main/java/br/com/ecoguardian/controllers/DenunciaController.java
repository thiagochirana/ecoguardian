package br.com.ecoguardian.controllers;

import br.com.ecoguardian.models.records.DenunciaJSON;
import br.com.ecoguardian.services.DenunciaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/denuncia")
public class DenunciaController {

    @Autowired
    private ViewBase view;

    @Autowired
    private DenunciaService denuncias;

    @GetMapping
    public ModelAndView getTelaDashboardDenuncia(){
        ModelAndView model = view.novaView("denuncia/denuncia");
        model.addObject("qtdeDenunciasAbertas",1);
        model.addObject("qtdeDenunciasEmAnalise",4);
        model.addObject("qtdeDenunciasPrecisaAtencao",2);
        model.addObject("todasDenuncias", denuncias.todas());
        return model;
    }

    @GetMapping("/nova")
    public ModelAndView novaDenuncia(){
        ModelAndView model = view.novaView("denuncia/novaDenuncia");
        return model;
    }

    @PostMapping("/nova")
    public ModelAndView registrarDenuncia(DenunciaJSON json, HttpServletRequest request){
        denuncias.salvar(json);
        return null;
    }
}
