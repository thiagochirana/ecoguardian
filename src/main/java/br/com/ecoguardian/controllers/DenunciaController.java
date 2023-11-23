package br.com.ecoguardian.controllers;

import br.com.ecoguardian.models.Categoria;
import br.com.ecoguardian.models.Denuncia;
import br.com.ecoguardian.models.Subcategoria;
import br.com.ecoguardian.models.records.DenunciaJSON;
import br.com.ecoguardian.models.records.MensagemView;
import br.com.ecoguardian.models.records.RegistroDenunciaJSON;
import br.com.ecoguardian.models.records.SubcategoriaDTO;
import br.com.ecoguardian.services.CategoriaService;
import br.com.ecoguardian.services.DenunciaService;
import br.com.ecoguardian.services.RegistroDenunciaService;
import br.com.ecoguardian.services.SessaoServiceWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/denuncia")
public class DenunciaController {

    @Autowired
    private ViewBase view;

    @Autowired
    private SessaoServiceWrapper sessaoServiceWrapper;

    @Autowired
    private CategoriaService categorias;

    @Autowired
    private DenunciaService denuncias;

    @Autowired
    private RegistroDenunciaService registros;

    @GetMapping
    public ModelAndView getTelaDashboardDenuncia(){
        ModelAndView model = view.novaView("denuncia/denuncia");
        model.addObject("todasDenuncias", denuncias.todasDoUsuarioLogado());
        model.addObject("usuarioLogadoIsAdminOuAnalista", sessaoServiceWrapper.getUsuarioLogado().isAdminOuAnalista());
        return model;
    }

    @GetMapping("/nova")
    public ModelAndView novaDenuncia(){
        ModelAndView model = view.novaView("denuncia/novaDenuncia");
        model.addObject("categorias",categorias.listar());
        model.addObject("notificacao", new MensagemView(false, false, null, null, null));
        return model;
    }

    @PostMapping("/novo")
    public ModelAndView registrarDenuncia(DenunciaJSON json){
        Denuncia den = denuncias.abrir(json);
        ModelAndView model = view.novaView("redirect:/denuncia");
        if (den != null){
            model.addObject("notificacao", new MensagemView(true, true, "Denuncia registrada com sucesso", "Aguarde um analista iniciar análise", null));
        } else {

            model.addObject("notificacao", new MensagemView(true, false, "Denuncia não foi registrada", "Ocorreu algo, tente mais tarde", null));
        }
        return model;
    }

//    @PostMapping("/{id}/registrar")
//    public ModelAndView carregarNovoFormParaRegistro(@PathVariable String id){
//        ModelAndView formRegistro = view.novaView("denuncia/registroTicketDenuncia");
//        Denuncia den = denuncias.obter(Long.parseLong(id));
//        if (den.getId() != null){
//            Denuncia d = den;
//            formRegistro.addObject("resumoDenuncia", "Denúncia feita por "+d.getDenunciante().getNome()+" na data e hora "+d.getDataAbertura().toString());
//            formRegistro.addObject("infoIdDenuncia","Denúncia de num. "+d.getId());
//            formRegistro.addObject("denunciaId",d.getId());
//            formRegistro.addObject("usuarioDenuncianteId",d.getDenunciante().getId());
//        } else {
//            formRegistro.addObject("resumoDenuncia", "Sem registros");
//            formRegistro.addObject("infoIdDenuncia","A iniciar nova Denuncia");
//            formRegistro.addObject("denunciaId",0L);
//            formRegistro.addObject("usuarioDenuncianteId",0L);
//        }
//        return formRegistro;
//    }

    @PostMapping("/registro/salvar")
    public ModelAndView realizarRegistro(RegistroDenunciaJSON json){
        registros.registrar(json);
        ModelAndView model = view.novaView("redirect:/denuncia/"+json.denunciaId()+"/verRegistros");
        return model;
    }

    @GetMapping("/{id}/registro/historico")
    public ModelAndView listarRegistros(@PathVariable String id){
        Denuncia denuncia = denuncias.obter(Long.parseLong(id));
        ModelAndView model = view.novaView("denuncia/historicoDeRegistroDenuncia");
        model.addObject("denuncia", denuncia);
        model.addObject("registros", registros.listarDaDenuncia(denuncia));
        return model;
    }

    @GetMapping("/{id}/registro/adicionarComentario")
    public ModelAndView obterFormParaRegistro(@PathVariable String id){
        Denuncia denuncia = denuncias.obter(Long.parseLong(id));
        ModelAndView model = view.novaView("denuncia/formRegistroDenuncia");
        model.addObject("denuncia",denuncia);
        return model;
    }

    @PostMapping("/registro/adicionarComentario/salvar")
    public ModelAndView salvarComentarioEmDenuncia(RegistroDenunciaJSON json){
        registros.registrar(json);
        ModelAndView model = view.novaView("redirect:/denuncia");
        return model;
    }

    @PostMapping("/{id}/registro/")
    public ModelAndView cadastrarComentario(RegistroDenunciaJSON json){
        return null;
    }

    @GetMapping("/categoria/{id}/subcategorias")
    @ResponseBody
    public List<SubcategoriaDTO> listarSubcategorias(@PathVariable Long id){
        return categorias.subcategoriasDaCategoriaId(id);
    }
}
