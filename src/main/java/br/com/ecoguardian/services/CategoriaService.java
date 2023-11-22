package br.com.ecoguardian.services;

import br.com.ecoguardian.models.Categoria;
import br.com.ecoguardian.repositories.CategoriaRepository;
import br.com.ecoguardian.repositories.SubcategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
