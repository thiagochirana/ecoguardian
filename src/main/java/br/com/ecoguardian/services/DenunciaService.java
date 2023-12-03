package br.com.ecoguardian.services;

import br.com.ecoguardian.models.*;
import br.com.ecoguardian.models.enums.Estado;
import br.com.ecoguardian.models.enums.StatusDenuncia;
import br.com.ecoguardian.models.records.DenunciaJSON;
import br.com.ecoguardian.repositories.DenunciaRepository;
import br.com.ecoguardian.utils.Datas;
import br.com.ecoguardian.utils.Log;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@Transactional
public class DenunciaService {

    @Autowired
    private DenunciaRepository denuncias;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LocalizacaoService localizacaoService;

    @Autowired
    private MunicipioService municipioService;

    @Autowired
    private RegistroDenunciaService registroDenunciaService;

    @Autowired
    private SessaoServiceWrapper sessaoServiceWrapper;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ArquivoService arquivos;

    Log LOG = new Log(DenunciaService.class);

    public Denuncia abrir(DenunciaJSON json, List<MultipartFile> listaArquivos){
        Municipio municipio = municipioService.obterMunicipio(json.idIBGE());
        Denuncia denNova = new Denuncia(json, json.sigilo() ? usuarioService.obterPeloId(5L) : usuarioService.obterPeloId(Long.parseLong(json.denuncianteId())), municipio);
        Localizacao local = localizacaoService.salvar(denNova.getLocalizacao());
        denNova.setLocalizacao(local);
        denNova.setCategoria(categoriaService.doId(json.categoriaId()));
        denNova.setSubcategoria(categoriaService.subcategoriaId(json.subcategoriaId()));
        Denuncia denSalva = denuncias.save(denNova);
        RegistroDenuncia registro = registroDenunciaService.abrir(denSalva);
        gerarNumeroProtocolo(denSalva);
        denSalva.adicionarRegistro(registro);
        return denuncias.save(arquivos.salvarArquivosDaDenuncia(denSalva, listaArquivos));
    }

    public void abrirDenunciaAnonima(DenunciaJSON json, List<MultipartFile> listaArquivos){
        Municipio municipio = municipioService.obterMunicipio(json.idIBGE());
        Denuncia denNova = new Denuncia(json, usuarioService.obterNovoAnonimo(), municipio);
        Localizacao local = localizacaoService.salvar(denNova.getLocalizacao());
        denNova.setLocalizacao(local);
        denNova.setCategoria(categoriaService.doId(json.categoriaId()));
        denNova.setSubcategoria(categoriaService.subcategoriaId(json.subcategoriaId()));
        Denuncia denSalva = denuncias.save(denNova);
        RegistroDenuncia registro = registroDenunciaService.abrir(denSalva);
        gerarNumeroProtocolo(denSalva);
        denSalva.adicionarRegistro(registro);
        denuncias.save(arquivos.salvarArquivosDaDenuncia(denSalva, listaArquivos));
    }

    public Denuncia abrir(DenunciaJSON json){
        return this.abrir(json, null);
    }

    public Denuncia abrirComArquivos(DenunciaJSON json, List<Arquivo> imagens){
        Municipio municipio = municipioService.obterMunicipio(json.idIBGE());
        Denuncia denNova = new Denuncia(json, json.sigilo() ? usuarioService.obterPeloId(5L) : usuarioService.obterPeloId(Long.parseLong(json.denuncianteId())), municipio);
        Localizacao local = localizacaoService.salvar(denNova.getLocalizacao());
        denNova.setLocalizacao(local);
        denNova.setCategoria(categoriaService.doId(json.categoriaId()));
        denNova.setSubcategoria(categoriaService.subcategoriaId(json.subcategoriaId()));
        Denuncia denSalva = denuncias.save(denNova);
        RegistroDenuncia registro = registroDenunciaService.abrir(denSalva);
        gerarNumeroProtocolo(denSalva);
        denSalva.adicionarRegistro(registro);
        return denuncias.save(arquivos.salvarArquivosTratadosDaDenuncia(denSalva, imagens));
    }

    public Denuncia obter(Long id){
        return denuncias.findById(id).orElseGet(Denuncia::new);
    }

    public Denuncia alterar(Denuncia denuncia){
        return denuncias.save(denuncia);
    }

    public List<Denuncia> doEstado(Estado estado){
        Optional<List<Denuncia>> lista = denuncias.doEstado(estado);
        return lista.orElseGet(ArrayList::new);
    }

    public List<Denuncia> doMunicipio(Municipio municipio){
        Optional<List<Denuncia>> lista = denuncias.doMunicipio(municipio);
        return lista.orElseGet(ArrayList::new);
    }

    public List<Denuncia> doUsuario(Usuario usuario){
        return denuncias.listarTodosDoUsuario(usuario).orElseGet(ArrayList::new);
    }

    public List<Denuncia> listarTodasDoUsuarioComSituacao(Usuario usuario, StatusDenuncia status){
        return denuncias.listarTodasDoUsuarioComStatus(usuario, status).orElseGet(ArrayList::new);
    }

    public List<Denuncia> todasDoUsuarioLogado(){
        try{
            if (sessaoServiceWrapper.getUsuarioLogado().isAdminOuAnalista() || sessaoServiceWrapper.getUsuarioLogado().temAcessoTotal()){
                return denuncias.findAll();
            } else {
                return denuncias.listarTodosDoUsuario(sessaoServiceWrapper.getUsuarioLogado()).orElseGet(ArrayList::new);
            }
        } catch (NullPointerException nulo){
            LOG.warn("Usuario Logado está nulo", nulo);
            return new ArrayList<>();
        }
    }

    public List<Denuncia> todasAbertasDoUsuarioLogado(){
        return denuncias.listarTodasEmAbertoDoUsuario(sessaoServiceWrapper.getUsuarioLogado()).orElseGet(ArrayList::new);
    }


    public List<Denuncia> todasEmAnaliseDoAnalista(){
        Set<Denuncia> den = new HashSet<>();
        for (RegistroDenuncia rd : registroDenunciaService.todasAtualizadasPeloAnalista()){
            if (rd.getDenuncia().getStatusDenuncia() == StatusDenuncia.EM_ANALISE || rd.getDenuncia().getStatusDenuncia() == StatusDenuncia.ANALISE_INICIADA ){
                den.add(rd.getDenuncia());
            }
        }
        return den.stream().toList();
    }

    public List<Denuncia> todasAbertasOuEmAnalise(){
        List<Denuncia> lista = new ArrayList<>();
        lista.addAll(denuncias.todasAbertas().orElseGet(ArrayList::new));
        List<Denuncia> lisAux = todasEmAnaliseDoAnalista();
        lista.addAll(lisAux);
        return lista;
    }

    public List<Denuncia> listarTodas(){
        return denuncias.findAll();
    }

    public void gerarNumeroProtocolo(Denuncia denuncia){
        denuncia.setProtocolo(getIdFormatado(denuncia.getId())+"/"+ Datas.getAnoAtual());
    }

    private String getIdFormatado(Long id){
        if (id <= 9){
            return "00"+id;
        } else if (id <= 99){
            return "0"+id;
        }
        return id+"";
    }

}
