package br.com.ecoguardian.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany
    private List<Atividade> atividadeAmbientais;

    public Categoria(){
        this.atividadeAmbientais = new ArrayList<>();
    }
}


@Entity
@Getter
@Setter
@Table(name = "atividade")
class Atividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    public void setId(Long id) {
        this.id = id;
    }

    public Atividade(){}

}
