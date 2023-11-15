package br.com.ecoguardian.models;

import br.com.ecoguardian.models.enums.TipoPerfil;
import jakarta.persistence.*;

@Entity
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoPerfil tipoPerfil;

    public Perfil(){
    }

    public Perfil(TipoPerfil tipoPerfil){
        this.tipoPerfil = tipoPerfil;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public boolean temAcessoTotal(){
        return tipoPerfil == TipoPerfil.ADMIN;
    }

    public void setTipoPerfil(TipoPerfil tipoPerfil){
        this.tipoPerfil = tipoPerfil;
    }

    public TipoPerfil getTipoPerfil(){
        return this.tipoPerfil;
    }
}
