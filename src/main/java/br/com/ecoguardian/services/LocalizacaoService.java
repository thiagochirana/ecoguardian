package br.com.ecoguardian.services;

import br.com.ecoguardian.models.Endereco;
import br.com.ecoguardian.models.Localizacao;
import br.com.ecoguardian.models.Municipio;
import br.com.ecoguardian.models.records.localizacao.CoordenadasDTO;
import br.com.ecoguardian.repositories.LocalizacaoRepository;
import br.com.ecoguardian.utils.Geolocalizacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocalizacaoService {

    @Autowired
    private LocalizacaoRepository localizacao;

    @Autowired
    private MunicipioService municipioService;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private Geolocalizacao geolocalizacao;

    public Localizacao salvar(Localizacao local){
        local.setMunicipio(municipioService.salvar(local.getMunicipio()));
        local.setEndereco(enderecoService.salvar(local.getEndereco()));
        return localizacao.save(local);
    }

    public Localizacao salvar(Localizacao local, Municipio mun, Endereco endereco){
        local.setMunicipio(municipioService.salvar(mun));
        local.setEndereco(enderecoService.salvar(endereco));
        return localizacao.save(local);
    }

    public CoordenadasDTO obterCoordenadasDoEndereco(String enderecoCompleto){
        return geolocalizacao.getCoordenadas(enderecoCompleto);
    }

    public String tratarEnderecoTeste(String enderecoCru){
        return geolocalizacao.tratarEndereco(enderecoCru, true);
    }
}
