package br.com.ecoguardian.services;

import br.com.ecoguardian.models.Unidade;
import br.com.ecoguardian.models.Usuario;
import br.com.ecoguardian.repositories.UnidadeRepository;
import br.com.ecoguardian.repositories.UsuarioRepository;
import br.com.ecoguardian.utils.CPFUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarios;

    @Autowired
    private UnidadeRepository unidades;

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

    public Optional<Usuario> vincularUnidadeAUsuario(Unidade unidade, Usuario usuario){
        Unidade un = unidades.save(unidade);
        usuario.adicionarUnidadeAUsuario(un);
        Usuario us = usuarios.save(usuario);
        return Optional.of(us);
    }


    public void ativarOuDesativar(String id, boolean atividade){
        Usuario us = usuarios.findById(Long.parseLong(id)).get();
        us.setAtivo(atividade);
        usuarios.save(us);
    }

}
