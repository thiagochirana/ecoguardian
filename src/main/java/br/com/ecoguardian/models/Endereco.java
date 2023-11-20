package br.com.ecoguardian.models;

import br.com.ecoguardian.models.enums.Estado;
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

    @OneToMany
    private List<Localizacao> localizacoesAssociadas;

    public Endereco(){
    }

    public Endereco(String logradouro, String bairro, String pontoDeReferencia){
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.pontoDeReferencia = pontoDeReferencia;
    }

    public Endereco(String logradouro, String bairro, String CEP, String pontoDeReferencia){
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.CEP = CEP;
        this.pontoDeReferencia = pontoDeReferencia;
    }

    public Endereco(String logradouro, String numero, String CEP, String bairro, String pontoDeReferencia) {
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.numero = numero;
        this.CEP = CEP;
        this.pontoDeReferencia = pontoDeReferencia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Endereco endereco)) return false;
        return Objects.equals(id, endereco.id) && Objects.equals(logradouro, endereco.logradouro) && Objects.equals(bairro, endereco.bairro) && Objects.equals(numero, endereco.numero) && Objects.equals(CEP, endereco.CEP) && Objects.equals(pontoDeReferencia, endereco.pontoDeReferencia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, logradouro, bairro, numero, CEP, pontoDeReferencia);
    }
}
