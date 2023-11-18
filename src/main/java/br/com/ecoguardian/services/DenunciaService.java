package br.com.ecoguardian.services;

import br.com.ecoguardian.models.Denuncia;
import br.com.ecoguardian.models.Municipio;
import br.com.ecoguardian.models.Usuario;
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

    public Denuncia salvar(DenunciaJSON json){
        return denuncias.save(new Denuncia(json));
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
