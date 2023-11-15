package br.com.ecoguardian.controllers;

import br.com.ecoguardian.models.Unidade;
import br.com.ecoguardian.models.Usuario;
import br.com.ecoguardian.models.records.LoginJSON;
import br.com.ecoguardian.services.LoginService;
import br.com.ecoguardian.services.UnidadeService;
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

    @GetMapping
    public String getHomePage() {
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute LoginJSON json, HttpServletRequest request){
        LOG.request(request);
        String cpf = json.cpf();
        String senha = json.senha();

        ModelAndView mav = new ModelAndView();
        mav.addObject("mostrarErro", false);

        Optional<Usuario> usuarioLogado = usuarios.validarObterUsuario(cpf,senha);

        if (usuarioLogado.isPresent() && logins.senhaPertenceAUser(usuarioLogado.get(), senha)){
            mav.setViewName("dashboard");
        } else {
            mav.setViewName("login"); // Página de login
            mav.addObject("mensagem", "Credenciais inválidas. Tente novamente.");
            mav.addObject("mostrarErro", true);
        }
        return mav;
    }

    @GetMapping("/selecionarUnidade")
    public String selecionarUnidade(Model model){
//        model.addAttribute("unidades",logins.listarTodosDoUsuarioLogado());
        model.addAttribute("unidades", Arrays.asList("Opção 1", "Opção 2", "Opção 3"));
        return "selecionarUnidade";
    }

    @GetMapping("/dashboard")
    public String getDashboard(Model model){
        return "/dashboard";
    }
    
}
