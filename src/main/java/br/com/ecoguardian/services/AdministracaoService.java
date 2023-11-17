package br.com.ecoguardian.services;

import br.com.ecoguardian.apis.Request;
import br.com.ecoguardian.models.Municipio;
import br.com.ecoguardian.models.records.MunicipioDTO;
import br.com.ecoguardian.models.records.NovaUnidadeJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministracaoService {

    @Autowired
    private MunicipioService municipioService;


    @Autowired
    private UsuarioService usuarioService;


}
