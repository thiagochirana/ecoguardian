package br.com.ecoguardian.models;

import br.com.ecoguardian.models.enums.Estado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class Localizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Municipio municipio;

    @OneToOne
    private Endereco endereco;

    private String latitude;

    private String longitude;

    public Localizacao(Municipio municipio){
        this.municipio = municipio;
    }

    public Localizacao(){}

    public Localizacao(Municipio municipio, Endereco endereco){
        this.municipio = municipio;
        this.endereco = endereco;
    }
}
