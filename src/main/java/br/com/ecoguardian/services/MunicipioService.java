package br.com.ecoguardian.services;

import br.com.ecoguardian.apis.Request;
import br.com.ecoguardian.models.Municipio;
import br.com.ecoguardian.models.records.MunicipioDTO;
import br.com.ecoguardian.repositories.MunicipioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MunicipioService {

    @Autowired
    private Request requestAPI;

    @Autowired
    private MunicipioRepository municipios;

    public Municipio salvar(Municipio municipio){
        return municipios.save(municipio);
    }

    public Municipio obterMunicipio(String idIBGE){
        Optional<Municipio> munOpt = municipios.obterByIdIBGE(Long.parseLong(idIBGE));
        if (munOpt.isPresent()){
            return munOpt.get();
        }
        Municipio m = new Municipio(requestAPI.obterMunicipioByIdIBGE(idIBGE));
        return municipios.save(m);
    }

    public List<Municipio> listarMunicipiosDaUF(String id){
        List<Municipio> lista = new ArrayList<>();
        for (MunicipioDTO m : requestAPI.municipiosDoEstado(id)){
            lista.add(new Municipio(m));
        }
        return lista;
    }
}
