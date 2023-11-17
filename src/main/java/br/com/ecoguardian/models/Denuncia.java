package br.com.ecoguardian.models;

import br.com.ecoguardian.models.enums.StatusDenuncia;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
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

    private Date dataAbertura;

    @OneToOne
    private Localizacao localizacao;

    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @OneToOne
    private Usuario denunciante;

    @Column(columnDefinition = "TEXT")
    private String outrasInformacoes;

    private String provavelAutorNome;

    @Column(columnDefinition = "TEXT")
    private String provavelAutorDescricao;

    @Enumerated(EnumType.STRING)
    private StatusDenuncia statusDenuncia;

    public Denuncia(){}

}
