package br.com.ecoguardian.controllers;

import br.com.ecoguardian.apis.Request;
import br.com.ecoguardian.models.*;
import br.com.ecoguardian.models.records.MunicipioDTO;
import br.com.ecoguardian.models.records.NovaUnidadeJSON;
import br.com.ecoguardian.models.records.NovoUsuarioJSON;
import br.com.ecoguardian.models.enums.TipoPerfil;
import br.com.ecoguardian.repositories.MunicipioRepository;
import br.com.ecoguardian.repositories.UsuarioRepository;
import br.com.ecoguardian.services.AdministracaoService;
import br.com.ecoguardian.services.UsuarioService;
import br.com.ecoguardian.utils.CPFUtils;
import br.com.ecoguardian.utils.Cripto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdministracaoController {

    @Autowired
    private ViewBase base;

    @Autowired
    private UsuarioService usuarios;

    @GetMapping
    public ModelAndView getAdminPage(){
        ModelAndView modelAndView = base.novaView("administracao");
        modelAndView.addObject("usuarios", usuarios.listarTodos());
        modelAndView.addObject("tiposPerfil", List.of(TipoPerfil.values()));
        return modelAndView;
    }

    @PostMapping("/novoUsuario")
    public String novoUser(NovoUsuarioJSON json){
        usuarios.salvar(json);
        return "redirect:/admin";
    }

    @GetMapping("localizacao")
    public ModelAndView localizacaoTeste(){
        return new ModelAndView("localizacao/localizacao");
    }

}
