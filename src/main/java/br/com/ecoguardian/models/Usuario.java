package br.com.ecoguardian.models;

import br.com.ecoguardian.models.enums.TipoPerfil;
import br.com.ecoguardian.utils.CPFUtils;
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
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String CPF;

    private String senha;

    @OneToMany
    private List<Criptografia> criptografias = new ArrayList<>();

    @OneToMany
    private List<Denuncia> denunciasFeitas = new ArrayList<>();

    @OneToMany
    private List<RegistroDenuncia> registrosFeitos = new ArrayList<>();

    @Column(nullable = true)
    private String email;

    @Column(nullable = true)
    private String telefone;

    @Column(nullable = true)
    private Boolean ativo;

    @Enumerated(EnumType.STRING)
    private TipoPerfil tipoPerfil;

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

    public boolean isAnalista(){
        return this.tipoPerfil == TipoPerfil.ANALISTA;
    }

    public boolean isAdmin(){
        return this.tipoPerfil == TipoPerfil.ADMIN;
    }

    public boolean isAdminOuAnalista(){
        return this.tipoPerfil == TipoPerfil.ANALISTA || this.tipoPerfil == TipoPerfil.ADMIN;
    }

    public boolean isDenunciante(){
        return this.tipoPerfil == TipoPerfil.DENUNCIANTE || this.tipoPerfil == TipoPerfil.ANONIMO;
    }

    public String getCPFcomMascara(){
        return CPFUtils.inserirMascara(this.CPF);
    }

    public String getNome(){
        return this.nome;
    }

    public void adicionarCriptografia(Criptografia criptografia){
        criptografias.add(criptografia);
    }
}
