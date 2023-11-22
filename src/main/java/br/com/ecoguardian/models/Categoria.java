package br.com.ecoguardian.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany
    private List<Subcategoria> subcategorias = new ArrayList<>();

    public Categoria(){}

    public void adicionarSubcategoria(Subcategoria subcategoria){
        subcategorias.add(subcategoria);
    }
}


