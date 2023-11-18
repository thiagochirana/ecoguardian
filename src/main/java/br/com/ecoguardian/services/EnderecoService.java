package br.com.ecoguardian.services;

import br.com.ecoguardian.models.Endereco;
import br.com.ecoguardian.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecos;

    public Endereco salvar(Endereco endereco){
        return enderecos.save(endereco);
    }
}
