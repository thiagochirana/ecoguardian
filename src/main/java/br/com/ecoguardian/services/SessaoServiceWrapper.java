package br.com.ecoguardian.services;

import br.com.ecoguardian.models.Usuario;
import br.com.ecoguardian.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class SessaoServiceWrapper {

    private Usuario usuarioLogado;

    @Autowired
    private UsuarioRepository usuarios;

    public void limparDetachedUsuarioLogado(){
        usuarios.flush();
        if (usuarioLogado != null) {
            usuarios.save(usuarioLogado);
        }
    }
}
