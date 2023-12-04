package br.com.ecoguardian.models;

import br.com.ecoguardian.models.enums.StatusDenuncia;
import br.com.ecoguardian.models.records.DenunciaJSON;
import br.com.ecoguardian.models.records.DenunciaRespJSON;
import br.com.ecoguardian.utils.Datas;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class Denuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean sigilo = false;

    private String protocolo;

    private Date dataAbertura;

    private Date dataOcorrencia;

    @ManyToOne(cascade = CascadeType.ALL)
    private Localizacao localizacao;

    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Usuario denunciante;

    @ManyToOne(cascade = CascadeType.ALL)
    private Categoria categoria;

    @ManyToOne(cascade = CascadeType.ALL)
    private Subcategoria subcategoria;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Arquivo> imagens;

    @Column(columnDefinition = "TEXT")
    private String outrasInformacoes;

    private String provavelAutorNome;

    @Column(columnDefinition = "TEXT")
    private String provavelAutorDescricao;

    @Enumerated(EnumType.STRING)
    private StatusDenuncia statusDenuncia;

    @OneToMany
    private List<RegistroDenuncia> registrosFeitos = new ArrayList<>();

    public Denuncia(){}

    public Denuncia(DenunciaJSON json, Usuario denunciante, Municipio municipio){
        this.sigilo = json.sigilo();
        this.dataAbertura = Datas.agora();
        Endereco endereco = new Endereco(json.logradouro(), json.numero(), json.CEP(), json.bairro(), json.pontoDeReferencia());
        this.localizacao = new Localizacao(municipio, endereco, json.latitude(), json.longitude());
        this.titulo = json.titulo();
        this.descricao = json.descricao();
        this.denunciante = denunciante;
        this.denunciante.setEmail(json.email());
        this.denunciante.setTelefone(json.telefone());
        this.outrasInformacoes = json.outrasInformacoes();
        this.provavelAutorNome = json.provavelAutorNome();
        this.provavelAutorDescricao = json.provavelAutorDescricao();
        this.dataOcorrencia = Datas.emStringParaDate(json.dataOcorrencia());
        this.statusDenuncia = StatusDenuncia.ABERTA;
    }

    public void adicionarRegistro(RegistroDenuncia registro){
        registro.setDenuncia(this);
        registrosFeitos.add(registro);
    }

    public String dataHoraDeAbertura(){
        return Datas.dataFormatada(this.dataAbertura);
    }

    @Transactional
    public List<Arquivo> getImagens(){
        return this.imagens;
    }

    public boolean precisaIniciar(){
        return this.statusDenuncia == StatusDenuncia.ABERTA || this.statusDenuncia == StatusDenuncia.AGUARDANDO_ANALISE;
    }

    public DenunciaRespJSON getDadosDenuncia(){
        return new DenunciaRespJSON(
                this.id,
                this.sigilo,
                this.protocolo,
                this.titulo,
                this.descricao,
                Datas.dataFormatada(this.dataAbertura),
                precisaIniciar(),
                this.localizacao.getMunicipio().getNome(),
                this.localizacao.getMunicipio().getEstado().getNome(),
                this.statusDenuncia.getNome(),
                this.denunciante.getNome()
        );
    }
}
