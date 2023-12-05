package br.com.ecoguardian.controllers;

import br.com.ecoguardian.models.enums.Estado;
import br.com.ecoguardian.models.enums.StatusDenuncia;
import br.com.ecoguardian.models.enums.TipoPerfil;
import br.com.ecoguardian.models.records.MensagemView;
import br.com.ecoguardian.services.SessaoServiceWrapper;
import br.com.ecoguardian.utils.VersaoUpdater;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
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
        modelAndView.addObject("statusDenuncia", statusItens());
        modelAndView.addObject("todosStatusDenuncia", StatusDenuncia.values());
        modelAndView.addObject("notificacao", new MensagemView(false, true,null, null, null));
        modelAndView.addObject("notificacaoTopDir", new MensagemView(false, true,null, null, null));
        modelAndView.addObject("appVersion", VersaoUpdater.VersaoManager.getVersaoAtual());
        return modelAndView;
    }

    public ModelAndView mensagemDeErro(Exception e, int codigoErro){
        ModelAndView model = new ModelAndView("errors/showError");
        model.addObject("mensagemDeErro", e.getMessage());
        model.addObject("codigo", codigoErro);
        return model;
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

    private List<StatusDenuncia> statusItens(){
        StatusDenuncia[] statusArray = StatusDenuncia.values();
        int indexOfFechada = -1;
        for (int i = 0; i < statusArray.length; i++) {
            if (statusArray[i] == StatusDenuncia.FECHADA) {
                indexOfFechada = i;
                break;
            }
        }
        if (indexOfFechada != -1) {
            StatusDenuncia[] newArray = new StatusDenuncia[statusArray.length - 1];
            System.arraycopy(statusArray, 0, newArray, 0, indexOfFechada);
            System.arraycopy(statusArray, indexOfFechada + 1, newArray, indexOfFechada, newArray.length - indexOfFechada);
            return List.of(newArray);
        }
        return List.of(StatusDenuncia.values());
    }

}
