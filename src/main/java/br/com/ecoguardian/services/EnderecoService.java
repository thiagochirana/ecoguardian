package br.com.ecoguardian.services;

import br.com.ecoguardian.models.Endereco;
import br.com.ecoguardian.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecos;

    public Endereco salvar(Endereco endereco){
        if (endereco.getId() == null){
            return enderecos.save(endereco);
        } else {
            Optional<Endereco> endSalvo = enderecos.findById(endereco.getId());
            if (endSalvo.isPresent() && endereco.equals(endSalvo.get())) {
                return endSalvo.get();
            }
            return enderecos.save(endereco);
        }
    }
}
