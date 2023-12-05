package br.com.ecoguardian.services;

import br.com.ecoguardian.models.*;
import br.com.ecoguardian.models.enums.Estado;
import br.com.ecoguardian.models.enums.StatusDenuncia;
import br.com.ecoguardian.models.records.DenunciaJSON;
import br.com.ecoguardian.models.records.DenunciaRespJSON;
import br.com.ecoguardian.models.records.DenunciasTableJSON;
import br.com.ecoguardian.models.records.EditarDenunciaJSON;
import br.com.ecoguardian.repositories.DenunciaRepository;
import br.com.ecoguardian.utils.Datas;
import br.com.ecoguardian.utils.Log;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    public void alterar(EditarDenunciaJSON json) {
        Optional<Denuncia> den = denuncias.findById(json.denunciaId());
        if (den.isPresent()){
            Denuncia d = den.get();
            d.setCategoria(categoriaService.doId(json.categoriaId()));
            d.setSubcategoria(categoriaService.subcategoriaId(json.subcategoriaId()));
            denuncias.save(d);
        }
    }

    public List<Denuncia> doEstado(Estado estado){
        Optional<List<Denuncia>> lista = denuncias.doEstado(estado);
        return lista.orElseGet(ArrayList::new);
    }

    public Optional<List<Denuncia>> doMunicipio(Municipio municipio){
        return denuncias.doMunicipio(municipio);
    }

    public List<Denuncia> daCategoria(Categoria categoria){
        return denuncias.findByCategoria(categoria).orElseGet(ArrayList::new);
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

    public ResponseEntity<DenunciasTableJSON> obterDenunciasParaTableDeAcordoComFiltro(String protocolo,
                                                                                             Long municipioId,
                                                                                             Long categoriaId,
                                                                                             String dataOcorrencia,
                                                                                             String dataCadastro,
                                                                                             StatusDenuncia status,
                                                                                             Boolean verSomenteUsuarioLogado){
        Set<Denuncia> listaDenunciaFiltradas = new HashSet<>();
        boolean protocoloPreenchido = protocolo != null && !protocolo.trim().isEmpty() && !protocolo.trim().isBlank();
        boolean municipioIdPreenchido = municipioId != null && municipioId > 0 ;
        boolean categoriaIdPreenchido = categoriaId != null && categoriaId > 0;
        boolean dataOcorrenciaPreenchido = dataOcorrencia != null && !dataOcorrencia.trim().isEmpty() && !dataOcorrencia.trim().isBlank();
        boolean dataCadastroPreenchido = dataCadastro != null && !dataCadastro.trim().isEmpty() && !dataCadastro.trim().isBlank();
        boolean somenteUserLogado = (verSomenteUsuarioLogado != null) && verSomenteUsuarioLogado;

        if (somenteUserLogado) {
            return ResponseEntity.ok(new DenunciasTableJSON(
                    sessaoServiceWrapper.getUsuarioLogado().temAcessoTotal(),
                    sessaoServiceWrapper.getUsuarioLogado().isAdminOuAnalista(),
                    listaToJSON(denuncias.listarTodosDoUsuario(sessaoServiceWrapper.getUsuarioLogado()).orElseGet(ArrayList::new))));
        }

        //Se não tem nenhum parametro, logo, deve retornar todas Denúncias
        if (!protocoloPreenchido && !municipioIdPreenchido && !categoriaIdPreenchido &&
        !dataOcorrenciaPreenchido && !dataCadastroPreenchido && status == null){
            List<Denuncia> lista = todasDoUsuarioLogado();
            if (!lista.isEmpty()){
                return ResponseEntity.ok(new DenunciasTableJSON(
                        sessaoServiceWrapper.getUsuarioLogado().temAcessoTotal(),
                        sessaoServiceWrapper.getUsuarioLogado().isAdminOuAnalista(),
                        listaToJSON(filtrarAsQueUsuarioPodeObservar(lista))));
            } else {
                return ResponseEntity.noContent().build();
            }
        }

        if (protocoloPreenchido){
            Optional<Denuncia> optionalDenuncia = denuncias.findByProtocolo(protocolo);
            optionalDenuncia.ifPresent(listaDenunciaFiltradas::add); //se tem algo ele adiciona

            // o return aqui já é imediato, porque protocolo é chave única
            return ResponseEntity.ok(new DenunciasTableJSON(
                    sessaoServiceWrapper.getUsuarioLogado().temAcessoTotal(),
                    sessaoServiceWrapper.getUsuarioLogado().isAdminOuAnalista(),
                    listaToJSON(filtrarAsQueUsuarioPodeObservar(listaDenunciaFiltradas.stream().toList()))
            ));
        }

        if (municipioIdPreenchido){
            Optional<Municipio> munOpt = municipioService.obterPeloId(municipioId);
            listaDenunciaFiltradas = realizarFiltragem(listaDenunciaFiltradas, doMunicipio(munOpt.get()));
        }

        if (categoriaIdPreenchido){
            Categoria categoria = categoriaService.doId(categoriaId);
            if (categoria.getId() != null){
                Optional<List<Denuncia>> lista = denuncias.findByCategoria(categoria);
                listaDenunciaFiltradas = realizarFiltragem(listaDenunciaFiltradas, lista);
            }
        }

        if (dataOcorrenciaPreenchido){
            Date data = Datas.emStringParaDate(dataOcorrencia);
            Optional<List<Denuncia>> listaPorData = denuncias.findByDataOcorrencia(data);
            listaDenunciaFiltradas = realizarFiltragem(listaDenunciaFiltradas, listaPorData);
        }

        if (dataCadastroPreenchido){
            Date data = Datas.emStringParaDate(dataCadastro);
            Optional<List<Denuncia>> listaPorData = denuncias.findByDataAbertura(data);
            listaDenunciaFiltradas = realizarFiltragem(listaDenunciaFiltradas, listaPorData);
        }

        if (status != null){
            Optional<List<Denuncia>> listaPorStatus = denuncias.findByStatusDenuncia(status);
            listaDenunciaFiltradas = realizarFiltragem(listaDenunciaFiltradas, listaPorStatus);
        }

        return ResponseEntity.ok(new DenunciasTableJSON(
                sessaoServiceWrapper.getUsuarioLogado().temAcessoTotal(),
                sessaoServiceWrapper.getUsuarioLogado().isAdminOuAnalista(),
                listaToJSON(filtrarAsQueUsuarioPodeObservar(listaDenunciaFiltradas.stream().toList()))));
    }

    //Exibir apenas aquelas que o Denunciante pode observar, se for Admin, Analista ou EcoGuardian, pode acessar todas denuncias
    private List<Denuncia> filtrarAsQueUsuarioPodeObservar(List<Denuncia> lista){
        if (sessaoServiceWrapper.getUsuarioLogado().temAcessoTotal() || sessaoServiceWrapper.getUsuarioLogado().isAdminOuAnalista()){
            return lista;
        }
        List<Denuncia> viewDen = new ArrayList<>();
        for (Denuncia den : lista) {
            if (den.getDenunciante().getId().equals(sessaoServiceWrapper.getUsuarioLogado().getId())){
                viewDen.add(den);
            }
        }
        return viewDen;
    }

    private List<DenunciaRespJSON> listaToJSON(List<Denuncia> listaResposta){
        List<DenunciaRespJSON> json = new ArrayList<>();
        for (Denuncia d : listaResposta){
            json.add(d.getDadosDenuncia());
        }
        return json;
    }

    /**
     * Função que serve para apenas para filtrar se o que tem na lista obtida contém na lista principal
     * Se tiver em ambas, então ele popula listaAux, filtrando ainda mais a lista
     * No final, ele retorna apenas aquelas que estão nas duas listas, o listaAux, realizando a filtragem
     */
    private Set<Denuncia> realizarFiltragem(Set<Denuncia> denunciasPreSalvas, Optional<List<Denuncia>> denunciasObtidas){
        Set<Denuncia> listaAux = new HashSet<>();
        if (!denunciasPreSalvas.isEmpty()){
            if (denunciasObtidas.isPresent()){
                for(Denuncia d : denunciasPreSalvas){
                    for (Denuncia d1 : denunciasObtidas.get()) {
                        if (d1.equals(d)){
                            listaAux.add(d);
                        }
                    }
                }
            } else {
                denunciasObtidas.ifPresent(listaAux::addAll);
            }
        } else {
            denunciasObtidas.ifPresent(listaAux::addAll);
        }
        return listaAux;
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
