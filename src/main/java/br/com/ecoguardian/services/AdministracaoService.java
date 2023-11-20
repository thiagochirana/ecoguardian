package br.com.ecoguardian.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministracaoService {

    @Autowired
    private MunicipioService municipioService;


    @Autowired
    private UsuarioService usuarioService;


}
