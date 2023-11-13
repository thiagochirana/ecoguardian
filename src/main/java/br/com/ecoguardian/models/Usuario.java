package br.com.ecoguardian.models;

import br.com.ecoguardian.models.enums.TipoPerfil;
import jakarta.persistence.*;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String CPF;

    private String senha;

    @OneToOne
    private Perfil perfil;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Usuario(){}

    public Usuario(String nome, String CPF, String senha, TipoPerfil tipoPerfil){
        this.nome = nome;
        this.senha = senha;
        this.perfil = new Perfil(tipoPerfil);
    }
}
