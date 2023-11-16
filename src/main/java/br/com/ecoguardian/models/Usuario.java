package br.com.ecoguardian.models;

import br.com.ecoguardian.models.enums.TipoPerfil;
import br.com.ecoguardian.utils.CPFUtils;
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

    @Column(nullable = true)
    private String email;

    @Column(nullable = true)
    private String telefone;

    @Column(nullable = true)
    private Boolean ativo;

    @Enumerated(EnumType.STRING)
    private TipoPerfil tipoPerfil;

    @ManyToMany
    private List<Unidade> unidadesPertencentes;

    public Usuario() {
        this.ativo = true;
    }

    public Usuario(String nome, TipoPerfil perfil){
        this.nome = nome;
        this.tipoPerfil = perfil;
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

    public boolean isAnalista(){
        return this.tipoPerfil == TipoPerfil.ANALISTA;
    }

    public boolean isAdmin(){
        return this.tipoPerfil == TipoPerfil.ADMIN;
    }

    public boolean isDenunciante(){
        return this.tipoPerfil == TipoPerfil.DENUNCIANTE;
    }

    public boolean isAnonimo(){
        return this.tipoPerfil == TipoPerfil.ANONIMO;
    }

    public String getCPFcomMascara(){
        return CPFUtils.inserirMascara(this.CPF);
    }
}
