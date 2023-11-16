package br.com.ecoguardian.models;

import br.com.ecoguardian.models.enums.Estado;
import br.com.ecoguardian.models.enums.TipoPerfil;
import br.com.ecoguardian.models.records.MunicipioDTO;
import br.com.ecoguardian.models.records.NovaUnidadeJSON;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToOne
    private Localizacao localizacao;

    @OneToMany
    private List<Usuario> listaDeAnalistas;

    public Unidade() {
        listaDeAnalistas = new ArrayList<>();
    }

    public Unidade(String nome, Localizacao localizacao){
        this.nome = nome;
        this.localizacao = localizacao;
        listaDeAnalistas = new ArrayList<>();
    }

    public Unidade(String nome, Localizacao localizacao, Usuario usuarioVinculado){
        this.nome = nome;
        this.localizacao = localizacao;
        listaDeAnalistas = new ArrayList<>();
        listaDeAnalistas.add(usuarioVinculado);
    }

    public Unidade(NovaUnidadeJSON json){
        this.nome = json.nome();
        Municipio mun = new Municipio(json.nome(), Long.parseLong(json.idIBGE()), json.estado());
        Endereco end = new Endereco(json.logradouro(), json.bairro(), json.pontoDeReferencia());
        this.localizacao = new Localizacao(mun, end);
    }

    public void adicionarAnalista(Usuario analista){
        if (analista.isAnalista()){
            listaDeAnalistas.add(analista);
        }
    }
}
