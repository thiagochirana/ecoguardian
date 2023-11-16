package br.com.ecoguardian.models;

import br.com.ecoguardian.models.enums.Estado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Table(name = "endereco")
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String logradouro;

    private String bairro;

    private String numero;

    private String CEP;

    private String pontoDeReferencia;

    public Endereco(){
    }

    public Endereco(String logradouro, String bairro, String pontoDeReferencia){
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.pontoDeReferencia = pontoDeReferencia;
    }

}
