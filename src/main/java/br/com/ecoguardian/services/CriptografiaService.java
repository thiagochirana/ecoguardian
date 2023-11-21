package br.com.ecoguardian.services;

import br.com.ecoguardian.models.Criptografia;
import br.com.ecoguardian.models.Usuario;
import br.com.ecoguardian.models.enums.ExecucaoCripto;
import br.com.ecoguardian.repositories.CriptografiaRepository;
import br.com.ecoguardian.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CriptografiaService {

    @Autowired
    private CriptografiaRepository criptografias;

    @Autowired
    private UsuarioRepository usuarios;

    public Criptografia salvar(Criptografia cripto){
        return criptografias.save(cripto);
    }

    public Criptografia atualizar(Criptografia criptografia){
        Criptografia cripSalvo = criptografias.save(criptografia);
        Usuario us = criptografia.getUsuario();
        us.adicionarCriptografia(criptografia);
        usuarios.save(us);
        return cripSalvo;
    }

    public void excluirDoUsuario(Usuario usuario) {
        Optional<List<Criptografia>> lista = criptografias.doUsuario(usuario);
        if (lista.isPresent()) {
            criptografias.deleteAll(lista.get());
        }
    }

    public List<Criptografia> criptografadas(){
        return criptografias.doTipoDeExecucaoDeCriptografia(ExecucaoCripto.CRIPTOGRAFIA).orElseGet(ArrayList::new);
    }

    public List<Criptografia> descriptografadas(){
        return criptografias.doTipoDeExecucaoDeCriptografia(ExecucaoCripto.DESCRIPTOGRAFIA).orElseGet(ArrayList::new);
    }

    public List<Criptografia> doUsuario(Usuario usuario){
        return criptografias.doUsuario(usuario).orElseGet(ArrayList::new);
    }

    public List<Criptografia> criptogradasDoUsuario(Usuario usuario){
        return criptografias.doTipoDeExecucaoDoUsuario(ExecucaoCripto.CRIPTOGRAFIA, usuario)
                .orElseGet(ArrayList::new);
    }

    public List<Criptografia> descriptografadasDoUsuario(Usuario usuario){
        return criptografias.doTipoDeExecucaoDoUsuario(ExecucaoCripto.DESCRIPTOGRAFIA, usuario)
                .orElseGet(ArrayList::new);
    }
}
