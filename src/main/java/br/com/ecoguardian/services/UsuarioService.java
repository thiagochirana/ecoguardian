package br.com.ecoguardian.services;

import br.com.ecoguardian.models.Unidade;
import br.com.ecoguardian.repositories.UnidadeRepository;
import br.com.ecoguardian.repositories.UsuarioRepository;
import br.com.ecoguardian.utils.CPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarios;

    @Autowired
    private UnidadeRepository unidades;

    @Autowired
    private UnidadeServiceWrapper unidadeWrapper;

    public boolean validarUsuario(String cpf, String senha){
        if (!CPF.validarCPF(cpf)){
            return false;
        }
        var user = usuarios.findByCPF(CPF.retirarMascara(cpf));
        if (user.isEmpty()){
            return false;
        }
        var unidadeUsuario = unidades.getUnidadeByUsuario(user.get());
        unidadeWrapper.setUnidadeLogada(unidadeUsuario.get());
        return true;
    }

}
