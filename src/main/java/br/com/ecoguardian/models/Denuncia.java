package br.com.ecoguardian.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class Denuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean sigilo = false;

    private Date dataDaDenuncia;

    @OneToOne
    private Localizacao localizacao;

    private String descricao;

    @OneToOne
    private Usuario denunciante;

    private String outrasInformacoes;

    private String provavelAutorNome;

    private String provavelAutorDescricao;

    public Denuncia(){}

}
