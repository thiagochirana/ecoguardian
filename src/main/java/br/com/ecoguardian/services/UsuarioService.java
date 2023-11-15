package br.com.ecoguardian.services;

import br.com.ecoguardian.models.Usuario;
import br.com.ecoguardian.repositories.UnidadeRepository;
import br.com.ecoguardian.repositories.UsuarioRepository;
import br.com.ecoguardian.utils.CPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarios;

    @Autowired
    private UnidadeRepository unidades;

    @Autowired
    private SessaoServiceWrapper sessaoServiceWrapper;

    public Optional<Usuario> validarObterUsuario(String cpf, String senha){
        if (!CPF.validarCPF(cpf)){
            return Optional.empty();
        }
        var user = usuarios.findByCPF(CPF.retirarMascara(cpf));
        if (user.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(user.get());
    }

}
