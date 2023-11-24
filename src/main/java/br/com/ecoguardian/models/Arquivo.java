package br.com.ecoguardian.models;

import br.com.ecoguardian.utils.Datas;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Arquivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] arquivo;

    private String nome;

    private String formato;

    private Date dataUpload;

    public Arquivo() {}

    public Arquivo(byte[] arquivo){
        this.arquivo = arquivo;
        this.dataUpload = Datas.agora();
    }

    public Arquivo(byte[] arquivo, String formato){
        this.arquivo = arquivo;
        this.formato = formato;
        this.dataUpload = Datas.agora();
    }

    public Arquivo(byte[] arquivo, String nome, String formato){
        this.arquivo = arquivo;
        this.nome = nome;
        this.formato = formato;
        this.dataUpload = Datas.agora();
    }
}
