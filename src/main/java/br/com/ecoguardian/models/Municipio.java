package br.com.ecoguardian.models;

import br.com.ecoguardian.models.enums.Estado;
import br.com.ecoguardian.models.records.MunicipioDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class Municipio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Long idIBGE;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @OneToMany
    private List<Localizacao> localizacoesAssociadas;

    public Municipio() {
    }

    public Municipio(MunicipioDTO municipio){
        this.nome = municipio.nome();
        this.idIBGE = municipio.id();
        this.estado = municipio.regiaoImediata().regiaoIntermediaria().UF().siglaUF();
    }

    public Municipio(String nome, Long idIBGE, Estado estado){
        this.nome = nome;
        this.idIBGE = idIBGE;
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Municipio municipio)) return false;
        return Objects.equals(id, municipio.id) && Objects.equals(nome, municipio.nome) && Objects.equals(idIBGE, municipio.idIBGE) && estado == municipio.estado;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, idIBGE, estado);
    }
}
