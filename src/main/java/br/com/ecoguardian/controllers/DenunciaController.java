package br.com.ecoguardian.controllers;

import br.com.ecoguardian.models.Arquivo;
import br.com.ecoguardian.models.Categoria;
import br.com.ecoguardian.models.Denuncia;
import br.com.ecoguardian.models.Subcategoria;
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
        model.addObject("todasDenuncias", denuncias.todasDoUsuarioLogado());
        model.addObject("usuarioLogadoIsAdminOuAnalista", sessaoServiceWrapper.getUsuarioLogado().isAdminOuAnalista());
        return model;
    }

    @Transactional
    @GetMapping("/{id}/visualizar")
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
    public ModelAndView registrarDenuncia(DenunciaJSON json,  @RequestParam("imagens") List<MultipartFile> imagens){
        Denuncia den = denuncias.abrir(json, imagens);
        ModelAndView model = view.novaView("redirect:/denuncia");
        if (den != null){
            model.addObject("notificacao", new MensagemView(true, true, "Denuncia registrada com sucesso", "Aguarde um analista iniciar análise", null));
        } else {

            model.addObject("notificacao", new MensagemView(true, false, "Denuncia não foi registrada", "Ocorreu algo, tente mais tarde", null));
        }
        return model;
    }

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

    @GetMapping("/verImagem/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> exibirImagem(@PathVariable Long id){
        return arquivos.obterImagem(id);
    }


}
