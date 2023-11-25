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
        if (municipio.getId() == null){
            return municipios.save(municipio);
        } else {
            Optional<Municipio> munSalvo = municipios.findById(municipio.getId());
            if (munSalvo.isPresent() && municipio.equals(munSalvo.get())) {
                return munSalvo.get();
            }
            return municipios.save(municipio);
        }
    }

    public Municipio obterMunicipio(String idIBGE){
        Optional<Municipio> munOpt = municipios.obterByIdIBGE(Long.parseLong(idIBGE));
        if (munOpt.isPresent()){
            return munOpt.get();
        }
        MunicipioDTO munDTO = requestAPI.obterMunicipioByIdIBGE(idIBGE);
        if (munDTO != null){
            return this.salvar(new Municipio(munDTO));
        } else {
            return new Municipio();
        }
    }

    public List<Municipio> listarMunicipiosDaUF(String id){
        List<Municipio> lista = new ArrayList<>();
        for (MunicipioDTO m : requestAPI.municipiosDoEstado(id)){
            lista.add(new Municipio(m));
        }
        return lista;
    }
}
