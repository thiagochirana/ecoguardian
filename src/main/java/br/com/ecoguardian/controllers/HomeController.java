package br.com.ecoguardian.controllers;

import br.com.ecoguardian.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UsuarioService usuarios;

    @GetMapping
    public String getHomePage() {
        return "index";
    }

    @PostMapping("/login")
    public String login(@RequestParam("cpf") String cpf,
                        @RequestParam("senha") String senha){

        return "redirect:/home/dashboard";
    }

    @GetMapping("/dashboard")
    public String getDashboard(){
        return "dashboard";
    }
    
}
