package br.com.ecoguardian.services;

import br.com.ecoguardian.models.Categoria;
import br.com.ecoguardian.models.Subcategoria;
import br.com.ecoguardian.models.records.SubcategoriaDTO;
import br.com.ecoguardian.repositories.CategoriaRepository;
import br.com.ecoguardian.repositories.SubcategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categorias;

    @Autowired
    private SubcategoriaRepository subcategorias;

    public Categoria salvar(Categoria categoria){
        categoria.setSubcategorias(subcategorias.saveAll(categoria.getSubcategorias()));
        return categorias.save(categoria);
    }

    public List<Categoria> listar(){
        return categorias.findAll();
    }

    public List<SubcategoriaDTO> subcategoriasDaCategoriaId(Long id){
        List<SubcategoriaDTO> dtos = new ArrayList<>();
        for (Subcategoria sub : subcategorias.daCategoria(categorias.findById(id).get()).orElseGet(ArrayList::new)){
            dtos.add(sub.getDTO());
        }
        return dtos;
    }

    public Categoria doId(Long id){
        return categorias.findById(id).orElseGet(Categoria::new);
    }

    public Subcategoria subcategoriaId(Long id){
        return subcategorias.findById(id).orElseGet(Subcategoria::new);
    }

}
