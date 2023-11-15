package br.com.ecoguardian.models;

import br.com.ecoguardian.models.enums.Estado;
import br.com.ecoguardian.models.enums.TipoPerfil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToOne
    private Localizacao localizacao;

    @OneToMany
    private List<Usuario> listaDeAnalistas;

    public Unidade() {
        listaDeAnalistas = new ArrayList<>();
    }

    public Unidade(String nome, Localizacao localizacao){
        this.nome = nome;
        this.localizacao = localizacao;
        listaDeAnalistas = new ArrayList<>();
    }

    public void adicionarAnalista(Usuario analista){
        if (analista.getPerfil().getTipoPerfil() == TipoPerfil.ANALISTA){
            listaDeAnalistas.add(analista);
        }
    }
}
