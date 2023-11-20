package br.com.ecoguardian.models;

import br.com.ecoguardian.models.enums.Estado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Localizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Municipio municipio;

    @ManyToOne(cascade = CascadeType.ALL)
    private Endereco endereco;

    @OneToMany
    private List<Denuncia> denuncias;

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

    public Localizacao(Municipio municipio, Endereco endereco, String latitude, String longitude){
        this.municipio = municipio;
        this.endereco = endereco;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
