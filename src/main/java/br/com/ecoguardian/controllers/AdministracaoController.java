package br.com.ecoguardian.controllers;

import br.com.ecoguardian.apis.Request;
import br.com.ecoguardian.models.Municipio;
import br.com.ecoguardian.models.Perfil;
import br.com.ecoguardian.models.Unidade;
import br.com.ecoguardian.models.Usuario;
import br.com.ecoguardian.models.records.MunicipioDTO;
import br.com.ecoguardian.models.records.NovaUnidadeJSON;
import br.com.ecoguardian.models.records.NovoUsuarioJSON;
import br.com.ecoguardian.models.enums.TipoPerfil;
import br.com.ecoguardian.repositories.PerfilRepository;
import br.com.ecoguardian.repositories.UnidadeRepository;
import br.com.ecoguardian.repositories.UsuarioRepository;
import br.com.ecoguardian.services.UsuarioService;
import br.com.ecoguardian.utils.CPF;
import br.com.ecoguardian.utils.Cripto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
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
    private UsuarioService usuarioService;

    @Autowired
    private PerfilRepository perfisDB;

    @Autowired
    private ViewBase base;

    @Autowired
    private Request requestAPI;

    @GetMapping
    public ModelAndView getAdminPage(){
        ModelAndView modelAndView = base.novaView("administracao");
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
        usuario.setCPF(CPF.retirarMascara(json.cpf()));
        usuario.setSenha(Cripto.criptografar(json.senha()).texto());
        perfisDB.save(usuario.getPerfil());
        usuariosDB.save(usuario);
        return "redirect:/admin";
    }

    @GetMapping("/municipios/{id}")
    @ResponseBody
    public List<MunicipioDTO> municipiosDoEstado(@PathVariable int id){
        return requestAPI.municipiosDoEstado(id);
    }

//    @PostMapping("/novaUnidade")
//    public String novaUnidade(NovaUnidadeJSON json){
//
//        Unidade unidade = new Unidade(json.nome(), json.estado());
//        usuarioService.vincularUnidadeAUsuario(unidade, json.usuarioVinculado());
//        return "redirect:/admin";
//    }

}
