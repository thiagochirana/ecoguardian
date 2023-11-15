package br.com.ecoguardian.controllers;

import br.com.ecoguardian.models.Usuario;
import br.com.ecoguardian.services.LoginService;
import br.com.ecoguardian.services.UsuarioService;
import br.com.ecoguardian.utils.Log;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    @ResponseBody
    public Map<String, Object> login(@RequestBody Map<String, String> json, HttpServletRequest request){
        LOG.request(request, json);
        String cpf = json.get("cpf");
        String senha = json.get("senha");

        Optional<Usuario> usuarioLogado = usuarios.validarObterUsuario(cpf,senha);
        Map<String, Object> response = new HashMap<>();
        if (!usuarioLogado.isEmpty()){
            if (logins.senhaPertenceAUser(usuarioLogado.get(), senha)){
                response.put("successo", true);
            } else {
                response.put("successo", false);
                response.put("mensagem", "Credenciais inválidas. Tente novamente.");
            }
        } else {
            response.put("successo", false);
            response.put("mensagem", "Credenciais inválidas. Tente novamente.");
        }

        LOG.info("Vou realizar a resposta "+response);
        return response;
    }

    @PostMapping("/selecionarUnidade")
    public void selecionarUnidade(@RequestBody Map<String, String> json){

    }

    @GetMapping("/dashboard")
    public String getDashboard(){
        return "/dashboard";
    }
    
}
