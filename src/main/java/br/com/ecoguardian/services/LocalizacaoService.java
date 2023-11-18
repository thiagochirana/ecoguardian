package br.com.ecoguardian.services;

import br.com.ecoguardian.models.Localizacao;
import br.com.ecoguardian.repositories.LocalizacaoRepository;
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

    public Localizacao salvar(Localizacao local){
        local.setMunicipio(municipioService.salvar(local.getMunicipio()));
        local.setEndereco(enderecoService.salvar(local.getEndereco()));
        return localizacao.save(local);
    }
}
