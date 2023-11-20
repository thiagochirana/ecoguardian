package br.com.ecoguardian.services;

import br.com.ecoguardian.models.*;
import br.com.ecoguardian.models.enums.Estado;
import br.com.ecoguardian.models.enums.StatusDenuncia;
import br.com.ecoguardian.models.records.DenunciaJSON;
import br.com.ecoguardian.repositories.DenunciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DenunciaService {

    @Autowired
    private DenunciaRepository denuncias;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LocalizacaoService localizacaoService;

    @Autowired
    private MunicipioService municipioService;

    public Denuncia salvar(DenunciaJSON json){
        Municipio municipio = municipioService.obterMunicipio(json.idIBGE());
        Denuncia denNova = new Denuncia(json,usuarioService.obterPeloId(Long.parseLong(json.denuncianteId())), municipio);
        denNova.getLocalizacao().setEndereco(new Endereco(json.logradouro(), json.numero(), json.CEP(), json.bairro(), json.pontoDeReferencia()));
        denNova.getLocalizacao().setMunicipio(municipio);
        Localizacao local = localizacaoService.salvar(denNova.getLocalizacao());
        denNova.setLocalizacao(local);
        return denuncias.save(denNova);
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

    public List<Denuncia> todas(){
        return denuncias.findAll();
    }

}
