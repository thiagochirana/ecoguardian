package br.com.ecoguardian.services;

import br.com.ecoguardian.models.Usuario;
import br.com.ecoguardian.repositories.UsuarioRepository;
import br.com.ecoguardian.utils.CPFUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarios;


    public Optional<Usuario> validarObterUsuario(String cpf, String senha){
        if (!CPFUtils.validarCPF(cpf)){
            return Optional.empty();
        }
        var user = usuarios.findByCPF(CPFUtils.retirarMascara(cpf));
        if (user.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(user.get());
    }


    public void ativarOuDesativar(String id, boolean atividade){
        Usuario us = usuarios.findById(Long.parseLong(id)).get();
        us.setAtivo(atividade);
        usuarios.save(us);
    }

}
