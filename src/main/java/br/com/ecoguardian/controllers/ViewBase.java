package br.com.ecoguardian.controllers;

import br.com.ecoguardian.models.enums.Estado;
import br.com.ecoguardian.models.enums.StatusDenuncia;
import br.com.ecoguardian.models.enums.TipoPerfil;
import br.com.ecoguardian.models.records.MensagemView;
import br.com.ecoguardian.services.SessaoServiceWrapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class ViewBase {

    @Autowired
    private SessaoServiceWrapper sessaoServiceWrapper;

    public ModelAndView novaView(String view){
        ModelAndView modelAndView = new ModelAndView(view);
        modelAndView.addObject("usuarioLogado", sessaoServiceWrapper.getUsuarioLogado());
        modelAndView.addObject("tiposPerfil", List.of(TipoPerfil.values()));
        modelAndView.addObject("estados", List.of(Estado.values()));
        modelAndView.addObject("statusDenuncia", List.of(StatusDenuncia.values()));
        modelAndView.addObject("notificacao", new MensagemView(false, true,null, null, null));
        modelAndView.addObject("notificacaoTopDir", new MensagemView(false, true,null, null, null));
        return modelAndView;
    }

    public ModelAndView retornoRequestOrigem(HttpServletRequest request, Map<String, Object> atributos){
        ModelAndView modelAndView = new ModelAndView();
        if (atributos != null) {
            modelAndView.addAllObjects(atributos);
        }
        String ref = request.getHeader("Referer");
        modelAndView.setViewName("redirect:" + ref);
        return modelAndView;
    }
}
