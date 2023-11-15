package br.com.ecoguardian.services;

import br.com.ecoguardian.models.Unidade;
import br.com.ecoguardian.models.Usuario;
import br.com.ecoguardian.utils.Cripto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    @Autowired
    private SessaoServiceWrapper sessaoServiceWrapper;

    @Autowired
    private UnidadeService unidadeService;

    public boolean senhaPertenceAUser(Usuario usuario, String senha){
        return senha.equals(Cripto.descriptografar(usuario.getSenha()).texto());
    }

    public List<Unidade> listarTodosDoUsuarioLogado(){
        return unidadeService.listarTodosDoUsuario(sessaoServiceWrapper.getUsuarioLogado());
    }

}