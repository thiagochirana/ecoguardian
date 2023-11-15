package br.com.ecoguardian.controllers;

import br.com.ecoguardian.models.enums.Estado;
import br.com.ecoguardian.models.enums.TipoPerfil;
import br.com.ecoguardian.repositories.PerfilRepository;
import br.com.ecoguardian.repositories.UnidadeRepository;
import br.com.ecoguardian.repositories.UsuarioRepository;
import br.com.ecoguardian.services.SessaoServiceWrapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
public class ViewBase {

    @Autowired
    private UnidadeRepository unidadesDB;

    @Autowired
    private SessaoServiceWrapper sessaoServiceWrapper;

    public ModelAndView novaView(String view){
        ModelAndView modelAndView = new ModelAndView(view);
        modelAndView.addObject("usuarioLogado", sessaoServiceWrapper.getUsuarioLogado());
        modelAndView.addObject("unidadeLogada", sessaoServiceWrapper.getUnidadeLogada());
        modelAndView.addObject("tiposPerfil", List.of(TipoPerfil.values()));
        modelAndView.addObject("estados", List.of(Estado.values()));
        modelAndView.addObject("unidadesDoUsuarioLogado", unidadesDB.getUnidadesByUsuario(sessaoServiceWrapper.getUsuarioLogado()));
        return modelAndView;
    }

    public ModelAndView retornoRequestOrigem(HttpServletRequest request, Map<String, Object> atributos){
        ModelAndView modelAndView = new ModelAndView();
        String ref = request.getHeader("Referer");
        modelAndView.setViewName("redirect:" + ref);
        if (atributos != null) {
            modelAndView.addAllObjects(atributos);
        }
        return modelAndView;
    }
}
