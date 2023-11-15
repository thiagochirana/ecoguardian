package br.com.ecoguardian.models;

import br.com.ecoguardian.models.enums.TipoPerfil;
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

    @OneToOne
    private Perfil perfil;

    @ManyToMany
    private List<Unidade> unidadesPertencentes;

    public Usuario() {}

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
