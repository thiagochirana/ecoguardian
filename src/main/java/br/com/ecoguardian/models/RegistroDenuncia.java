package br.com.ecoguardian.models;

import br.com.ecoguardian.models.enums.StatusDenuncia;
import br.com.ecoguardian.models.records.RegistroDenunciaJSON;
import br.com.ecoguardian.utils.Datas;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class RegistroDenuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Denuncia denuncia;

    @Enumerated(EnumType.STRING)
    private StatusDenuncia statusAtual;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Usuario quemAtualizou;

    @Column(columnDefinition = "TEXT")
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    private Date dataHoraRegistro;

    public RegistroDenuncia(String titulo, String descricao, Usuario usuario, Denuncia qualDenuncia, StatusDenuncia statusDenuncia){
        this.dataHoraRegistro = Datas.agora();
        this.titulo = titulo;
        this.descricao = descricao;
        this.quemAtualizou = usuario;
        this.denuncia = qualDenuncia;
        this.statusAtual = statusDenuncia;
    }

    public RegistroDenuncia(){
        this.dataHoraRegistro = Datas.agora();
    }

    public RegistroDenuncia(RegistroDenunciaJSON json, Usuario quemAtualizou, Denuncia qualDenuncia) {
        this.dataHoraRegistro = Datas.agora();
        this.titulo = json.titulo();
        this.descricao = json.descricao();
        this.quemAtualizou = quemAtualizou;
        this.denuncia = qualDenuncia;
        this.statusAtual = json.statusDenuncia();
    }

    public String dataHoraRegistroFormatada(){
        return Datas.dataFormatada(this.dataHoraRegistro);
    }
}
