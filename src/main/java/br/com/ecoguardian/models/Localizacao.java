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

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @OneToOne
    private Municipio municipio;

    @OneToOne
    private Endereco endereco;

    private String latitude;

    private String longitude;

    public Localizacao(Estado estado, Municipio municipio){
        this.estado = estado;
        this.municipio = municipio;
    }

    public Localizacao(){}
}
