package br.com.ecoguardian.controllers;

import br.com.ecoguardian.models.records.NovoUsuarioJSON;
import br.com.ecoguardian.models.enums.TipoPerfil;
import br.com.ecoguardian.services.ArquivoService;
import br.com.ecoguardian.services.BackOfficeService;
import br.com.ecoguardian.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/backOffice")
public class BackOfficeController {

    @Autowired
    private ViewBase base;

    @Autowired
    private UsuarioService usuarios;


    @Autowired
    private BackOfficeService backOfficeService;

    @Autowired
    private ArquivoService arquivoService;

    @GetMapping
    public ModelAndView getAdminPage(){
        ModelAndView modelAndView = base.novaView("contas/backOffice");
        modelAndView.addObject("usuarios", usuarios.listarTodos());
        modelAndView.addObject("tiposPerfil", List.of(TipoPerfil.values()));
        return modelAndView;
    }

    @PostMapping("/novoUsuario")
    public String novoUser(NovoUsuarioJSON json){
        usuarios.salvar(json);
        return "redirect:/backOffice";
    }

    @GetMapping("localizacao")
    public ModelAndView localizacaoTeste(){
        return new ModelAndView("localizacao/localizacao");
    }


    //Exemplo imagens
    @PostMapping("/upload")
    public String salvarArquivo(@RequestParam("arquivos") List<MultipartFile> arquivos){
        arquivoService.salvarArquivos(arquivos);
        return "redirect:/backOffice/imagens";
    }

    @GetMapping("/imagens")
    public ModelAndView abrirTelaImagens(){
        ModelAndView model = new ModelAndView("imagens");
        model.addObject("imagens", backOfficeService.listarTodos());
        return model;
    }

    @GetMapping("/verImagem/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> exibirImagem(@PathVariable Long id){
        return backOfficeService.obterImagem(id);
    }

}


