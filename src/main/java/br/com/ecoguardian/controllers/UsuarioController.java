package br.com.ecoguardian.controllers;

import br.com.ecoguardian.models.Usuario;
import br.com.ecoguardian.repositories.UsuarioRepository;
import br.com.ecoguardian.services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private ViewBase base;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/desativar/{id}")
    public ModelAndView desativar(@PathVariable String id, HttpServletRequest request){
        usuarioService.ativarOuDesativar(id, false);
        return base.retornoRequestOrigem(request, null);
    }

    @PostMapping("/ativar/{id}")
    public ModelAndView ativar(@PathVariable String id, HttpServletRequest request){
        usuarioService.ativarOuDesativar(id, true);
        return base.retornoRequestOrigem(request, null);
    }


    @PostMapping("/{id}")
    public String excluir(@PathVariable String id){
        usuarioService.excluir(id);
        return "redirect:/admin";
    }
}
