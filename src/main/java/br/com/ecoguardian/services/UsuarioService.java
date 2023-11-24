package br.com.ecoguardian.services;

import br.com.ecoguardian.models.Criptografia;
import br.com.ecoguardian.models.Usuario;
import br.com.ecoguardian.models.enums.ExecucaoCripto;
import br.com.ecoguardian.models.enums.TipoPerfil;
import br.com.ecoguardian.models.records.NovoUsuarioJSON;
import br.com.ecoguardian.models.records.SenhaCriptoDTO;
import br.com.ecoguardian.repositories.UsuarioRepository;
import br.com.ecoguardian.utils.CPFUtils;
import br.com.ecoguardian.utils.Cripto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarios;

    @Autowired
    private CriptografiaService criptografias;

    public Usuario salvar(NovoUsuarioJSON json){
        Usuario usuario = new Usuario(json.nome(), json.tipoPerfil());
        usuario.setCPF(CPFUtils.retirarMascara(json.cpf()));
        SenhaCriptoDTO senhaCripto = Cripto.criptografar(json.senha());
        Criptografia criptografia = new Criptografia(ExecucaoCripto.CRIPTOGRAFIA, senhaCripto);
        usuario.setSenha(senhaCripto.texto());
        usuario.setEmail(json.email());
        usuario.setTelefone(json.telefone());

        //Condicao feita para criacao de conta de denunciante, pois a informação no JSON vem com o tipo de Usuario null para cadastro de novos denunciates/usuarios
        if(usuario.getTipoPerfil() == null){
            usuario.setTipoPerfil(TipoPerfil.DENUNCIANTE);
        }

        Criptografia cripSalvo = criptografias.salvar(criptografia);
        usuario.adicionarCriptografia(cripSalvo);
        Usuario usSalvo =  usuarios.save(usuario);
        cripSalvo.setUsuario(usSalvo);
        criptografias.salvar(cripSalvo);
        return usSalvo;
    }

    public Usuario salvar(Usuario usuario){
        return usuarios.save(usuario);
    }

    public List<Usuario> listarTodos(){
        return usuarios.findAll();
    }

    public Optional<Usuario> validarObterUsuario(String cpf, String senha){
        if (!CPFUtils.validarCPF(cpf)){
            return Optional.empty();
        }
        var user = usuarios.findByCPF(CPFUtils.retirarMascara(cpf));
        if (user.isEmpty()){
            return Optional.empty();
        } else {

        }
        return Optional.of(user.get());
    }

    public Usuario obterPeloId(Long id){
        return usuarios.findById(id).orElseGet(Usuario::new);
    }


    public void ativarOuDesativar(String id, boolean atividade){
        Usuario us = usuarios.findById(Long.parseLong(id)).get();
        us.setAtivo(atividade);
        usuarios.save(us);
    }

    public void excluir(String id){
        Usuario us = usuarios.findById(Long.parseLong(id)).get();
        criptografias.excluirDoUsuario(us);
        usuarios.delete(us);

    }

}
