package br.com.ecoguardian.services;

import br.com.ecoguardian.models.*;
import br.com.ecoguardian.repositories.EnderecoRepository;
import br.com.ecoguardian.repositories.LocalizacaoRepository;
import br.com.ecoguardian.repositories.MunicipioRepository;
import br.com.ecoguardian.repositories.UnidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UnidadeService {

    @Autowired
    private UnidadeRepository unidades;

    @Autowired
    private MunicipioRepository municipios;

    @Autowired
    private LocalizacaoRepository localizacoes;

    @Autowired
    private EnderecoRepository enderecos;

    public List<Unidade> listarTodosDoUsuario(Usuario usuario){
        Optional<List<Unidade>> listaUnidades = unidades.getUnidadesByUsuario(usuario);
        return listaUnidades.orElseGet(ArrayList::new);
    }

    public Unidade salvarUnidade(Unidade unidade){
        Municipio munSalvo = municipios.save(unidade.getLocalizacao().getMunicipio());
        unidade.getLocalizacao().setMunicipio(munSalvo);
        Endereco endSalvo = enderecos.save(unidade.getLocalizacao().getEndereco());
        unidade.getLocalizacao().setEndereco(endSalvo);
        Localizacao localSalvo = localizacoes.save(unidade.getLocalizacao());
        unidade.setLocalizacao(localSalvo);
        return unidades.save(unidade);
    }
}
