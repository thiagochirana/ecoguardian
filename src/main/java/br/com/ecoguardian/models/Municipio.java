package br.com.ecoguardian.models;

import br.com.ecoguardian.models.enums.Estado;
import br.com.ecoguardian.models.records.MunicipioDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
}
