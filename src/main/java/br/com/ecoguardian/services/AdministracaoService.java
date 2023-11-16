package br.com.ecoguardian.services;

import br.com.ecoguardian.apis.Request;
import br.com.ecoguardian.models.Municipio;
import br.com.ecoguardian.models.Unidade;
import br.com.ecoguardian.models.records.MunicipioDTO;
import br.com.ecoguardian.models.records.NovaUnidadeJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministracaoService {

    @Autowired
    private MunicipioService municipioService;

    @Autowired
    private UnidadeService unidades;

    @Autowired
    private UsuarioService usuarioService;

    public void cadastrarNovaUnidade(NovaUnidadeJSON json){
        Municipio mun = municipioService.obterMunicipio(json.idIBGE());
        Unidade unidade = new Unidade(json);
        unidade.getLocalizacao().setMunicipio(mun);
        unidades.salvarUnidade(unidade);

        usuarioService.vincularUnidadeAUsuario(unidade, json.usuarioVinculado());
    }
}
