package br.com.ecoguardian.controllers;

import br.com.ecoguardian.models.Usuario;
import br.com.ecoguardian.models.records.LoginJSON;
import br.com.ecoguardian.models.records.MensagemView;
import br.com.ecoguardian.services.LoginService;
import br.com.ecoguardian.services.SessaoServiceWrapper;
import br.com.ecoguardian.services.UsuarioService;
import br.com.ecoguardian.utils.Log;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class HomeController {

    Log LOG = new Log(HomeController.class);

    @Autowired
    private UsuarioService usuarios;

    @Autowired
    private LoginService logins;

    @Autowired
    private SessaoServiceWrapper sessao;

    @Autowired
    private ViewBase view;

    @GetMapping
    public ModelAndView getHomePage() {
        return view.novaView("login");
    }

    @GetMapping("/login")
    public String redirectPaliativo(){
        sessao.setUsuarioLogado(null);
        return "redirect:/";
    }

    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute LoginJSON json, HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        if (json == null || !request.getMethod().equalsIgnoreCase("post")){
            mav.setViewName("login");
            return mav;
        }
        String cpf = json.cpf();
        String senha = json.senha();

        MensagemView msg = new MensagemView(false, true,null, null, null);

        mav.addObject("notificacao", false);

        Optional<Usuario> usuarioLogado = usuarios.validarObterUsuario(cpf,senha);

        if (usuarioLogado.isPresent() && logins.senhaPertenceAUser(usuarioLogado.get(), senha)){
            Usuario user = usuarioLogado.get();
            sessao.setUsuarioLogado(user);
            LOG.info("Realizando login do usuário "+user.getNome());
            return view.novaView("dashboard/dashboard");
        } else {
            mav.setViewName("login"); // Página de login
            msg = new MensagemView(true, false, "Ops! Ocorreu um erro", "Credenciais inválidas", null);
        }
        mav.addObject("notificacao", msg);
        return mav;
    }
    
}
