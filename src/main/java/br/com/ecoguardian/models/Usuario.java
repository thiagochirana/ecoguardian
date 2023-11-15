package br.com.ecoguardian.models;

import br.com.ecoguardian.models.enums.TipoPerfil;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String CPF;

    private String senha;

    private String email;

    private String telefone;

    @Column(nullable = true)
    private Boolean ativo;

    @OneToOne
    private Perfil perfil;

    @ManyToMany
    private List<Unidade> unidadesPertencentes;

    public Usuario() {
        this.ativo = true;
    }

    public Usuario(String nome, Perfil perfil){
        this.nome = nome;
        this.perfil = perfil;
        this.ativo = true;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void adicionarUnidadeAUsuario(Unidade unidade){
        unidadesPertencentes.add(unidade);
    }

}
