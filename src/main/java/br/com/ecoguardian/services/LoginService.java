package br.com.ecoguardian.services;

import br.com.ecoguardian.models.Criptografia;
import br.com.ecoguardian.models.Usuario;
import br.com.ecoguardian.models.enums.ExecucaoCripto;
import br.com.ecoguardian.models.records.SenhaCriptoDTO;
import br.com.ecoguardian.utils.Cripto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    @Autowired
    private CriptografiaService criptografias;

    @Autowired
    private UsuarioService usuarios;

    public boolean senhaPertenceAUser(Usuario usuario, String senha){
        SenhaCriptoDTO senhaCripto = Cripto.descriptografar(usuario.getSenha());
        if (senha.equals(senhaCripto.texto())){
            Criptografia descrip = new Criptografia(ExecucaoCripto.DESCRIPTOGRAFIA, senhaCripto);
            Criptografia dcSalvo = criptografias.salvar(descrip);
            usuario.adicionarCriptografia(dcSalvo);
            dcSalvo.setUsuario(usuarios.salvar(usuario));
            criptografias.salvar(dcSalvo);
            return true;
        }
        return false;
    }

}
