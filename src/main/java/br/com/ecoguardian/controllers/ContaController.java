package br.com.ecoguardian.controllers;

import br.com.ecoguardian.models.records.NovoUsuarioJSON;
import br.com.ecoguardian.services.SessaoServiceWrapper;
import br.com.ecoguardian.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/minhaConta")
public class ContaController {

    @Autowired
    private ViewBase view;

    @Autowired
    private UsuarioService usuarios;

    @Autowired
    private SessaoServiceWrapper sessao;

    @GetMapping
    public ModelAndView minhaConta(){
        ModelAndView model = view.novaView("contas/minhaConta");
        return model;
    }

    @GetMapping("/criar")
    public ModelAndView criarConta(){
        sessao.setUsuarioLogado(null);
        return view.novaView("contas/criarConta");
    }


    @PostMapping("/salvar")
    public String salvarUsuario(NovoUsuarioJSON json){
        usuarios.salvar(json);
        return "redirect:/login";
    }
}
