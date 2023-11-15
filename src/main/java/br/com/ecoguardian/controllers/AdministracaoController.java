package br.com.ecoguardian.controllers;

import br.com.ecoguardian.models.Perfil;
import br.com.ecoguardian.models.Unidade;
import br.com.ecoguardian.models.Usuario;
import br.com.ecoguardian.models.enums.NovoUsuarioJSON;
import br.com.ecoguardian.models.enums.TipoPerfil;
import br.com.ecoguardian.repositories.PerfilRepository;
import br.com.ecoguardian.repositories.UnidadeRepository;
import br.com.ecoguardian.repositories.UsuarioRepository;
import br.com.ecoguardian.utils.Cripto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdministracaoController {

    @Autowired
    private UnidadeRepository unidadesDB;

    @Autowired
    private UsuarioRepository usuariosDB;

    @Autowired
    private PerfilRepository perfisDB;

    @GetMapping
    public ModelAndView getAdminPage(){
        ModelAndView modelAndView = new ModelAndView("administracao");
        modelAndView.addObject("unidades",listarUnidades());
        modelAndView.addObject("usuarios",listarUsuarios());
        modelAndView.addObject("tiposPerfil",listagemTipoPerfil());
        return modelAndView;
    }

    public List<Unidade> listarUnidades(){
        return unidadesDB.findAll();
    }

    public List<Usuario> listarUsuarios(){
        return usuariosDB.findAll();
    }

    public List<TipoPerfil> listagemTipoPerfil(){
        return List.of(TipoPerfil.values());
    }

    @PostMapping("/novoUsuario")
    public String novoUser(NovoUsuarioJSON json){
        Usuario usuario = new Usuario(json.nome(), new Perfil(json.tipoPerfil()));
        usuario.setCPF(json.cpf());
        usuario.setSenha(Cripto.criptografar(json.senha()).texto());
        perfisDB.save(usuario.getPerfil());
        usuariosDB.save(usuario);
        return "redirect:/admin";
    }

}
