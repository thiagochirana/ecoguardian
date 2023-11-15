package br.com.ecoguardian.services;

import br.com.ecoguardian.models.Unidade;
import br.com.ecoguardian.models.Usuario;
import br.com.ecoguardian.repositories.UnidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UnidadeService {

    @Autowired
    private UnidadeRepository unidades;

    public List<Unidade> listarTodosDoUsuario(Usuario usuario){
        Optional<List<Unidade>> listaUnidades = unidades.getUnidadesByUsuario(usuario);
        return listaUnidades.orElseGet(ArrayList::new);
    }
}
