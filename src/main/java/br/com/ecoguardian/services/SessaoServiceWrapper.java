package br.com.ecoguardian.services;

import br.com.ecoguardian.models.Usuario;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class SessaoServiceWrapper {

    private Usuario usuarioLogado;

}
