package br.com.ecoguardian.controllers;

import br.com.ecoguardian.models.enums.TipoPerfil;
import br.com.ecoguardian.repositories.PerfilRepository;
import br.com.ecoguardian.repositories.UnidadeRepository;
import br.com.ecoguardian.repositories.UsuarioRepository;
import br.com.ecoguardian.services.SessaoServiceWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BaseController {

    @Autowired
    private UnidadeRepository unidadesDB;

    @Autowired
    private UsuarioRepository usuariosDB;

    @Autowired
    private PerfilRepository perfisDB;

    @Autowired
    private SessaoServiceWrapper sessaoServiceWrapper;

    public ModelAndView novaView(String view){
        ModelAndView modelAndView = new ModelAndView(view);
        modelAndView.addObject("usuarioLogado", sessaoServiceWrapper.getUsuarioLogado());
        modelAndView.addObject("unidadeLogada", sessaoServiceWrapper.getUnidadeLogada());
        modelAndView.addObject("tiposPerfil", List.of(TipoPerfil.values()));
        modelAndView.addObject("unidadesDoUsuarioLogado", unidadesDB.getUnidadesByUsuario(sessaoServiceWrapper.getUsuarioLogado()));
        return modelAndView;
    }
}
