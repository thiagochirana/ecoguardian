package br.com.ecoguardian.controllers;

import br.com.ecoguardian.models.Arquivo;
import br.com.ecoguardian.models.Denuncia;
import br.com.ecoguardian.models.enums.Estado;
import br.com.ecoguardian.models.records.DenunciaJSON;
import br.com.ecoguardian.models.records.MensagemView;
import br.com.ecoguardian.models.records.RegistroDenunciaJSON;
import br.com.ecoguardian.models.records.SubcategoriaDTO;
import br.com.ecoguardian.services.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    @Autowired
    private ArquivoService arquivos;

    @GetMapping
    public ModelAndView getTelaDashboardDenuncia(){
        ModelAndView model = view.novaView("denuncia/denuncia");
        model.addObject("denunciasFeitas", denuncias.todasAbertasDoUsuarioLogado());
        model.addObject("denunciasEmAnaliseAnalista", denuncias.todasAbertasOuEmAnalise());
        model.addObject("usuarioLogadoIsAdminOuAnalista", sessaoServiceWrapper.getUsuarioLogado().isAdminOuAnalista());
        return model;
    }

    @GetMapping("/{id}/visualizar")
    @Transactional
    public ModelAndView visualizarDenuncia(@PathVariable Long id){
        ModelAndView model = view.novaView("denuncia/visualizarDenuncia");
        Denuncia den = denuncias.obter(id);
        List<Arquivo> imagens = arquivos.listarTodosDaDenuncia(den);
        model.addObject("imagens", imagens);
        model.addObject("denuncia", den);
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
    public String registrarDenuncia(DenunciaJSON json,  @RequestParam("imagens") List<MultipartFile> imagens){
        denuncias.abrir(json, imagens);
        return "redirect:/denuncia";
    }

    @PostMapping("/registro/salvar")
    public ModelAndView realizarRegistro(RegistroDenunciaJSON json){
        registros.persistirAlteracoes(json);
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

    @PostMapping("/registro/adicionarComentario/aguardandoAnalise")
    public String salvarComentarioAguardandoAnalise(RegistroDenunciaJSON json){
        registros.salvarComentarioAguardandoAnalise(json);
        return "redirect:/denuncia";
    }

    @PostMapping("/registro/adicionarComentario/iniciarAnalise")
    public String iniciarAnalise(RegistroDenunciaJSON json){
        registros.iniciarAnalise(json);
        return "redirect:/denuncia";
    }

    @PostMapping("/registro/adicionarComentario/resolvida")
    public String encerrarAnalise(RegistroDenunciaJSON json){
        registros.analiseResolvida(json);
        return "redirect:/denuncia";
    }

    @PostMapping("/registro/adicionarComentario/rejeitar")
    public String rejeitarDenuncia(RegistroDenunciaJSON json){
        registros.rejeitarDenuncia(json);
        return "redirect:/denuncia";
    }

    @PostMapping("/registro/adicionarComentario/encerrar")
    public String encerrarDenuncia(RegistroDenunciaJSON json){
        registros.encerradaPeloUsuario(json);
        return "redirect:/denuncia";
    }

    @PostMapping("/registro/adicionarComentario/salvar")
    public String salvarComentarioEmDenuncia(RegistroDenunciaJSON json){
        registros.adicionarComentarioJaIniciado(json);
        return "redirect:/denuncia";
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

    @GetMapping("/verImagem/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> exibirImagem(@PathVariable Long id){
        return arquivos.obterImagem(id);
    }

    @GetMapping("/anonima")
    public ModelAndView getViewDenunciaAnonima(){
        ModelAndView model = new ModelAndView("denuncia/denunciaAnonima");
        model.addObject("estados", Estado.values());
        model.addObject("categorias",categorias.listar());
        return model;
    }

    @PostMapping("/anonima")
    public String salvarDenunciaAnonima(DenunciaJSON json,  @RequestParam("imagens") List<MultipartFile> imagens){
        denuncias.abrirDenunciaAnonima(json.getJSONparaAnonimo(), imagens);
        return "redirect:/login";
    }


}
