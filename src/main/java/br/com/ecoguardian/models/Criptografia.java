package br.com.ecoguardian.models;

import br.com.ecoguardian.models.enums.ExecucaoCripto;
import br.com.ecoguardian.models.records.SenhaCriptoDTO;
import br.com.ecoguardian.utils.Datas;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Criptografia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ExecucaoCripto tipoExecucao;

    private Long tempoDeExecucao;

    private Date data;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Criptografia(){
        this.data = Datas.agora();
    }

    public Criptografia(ExecucaoCripto tipoExecucao, SenhaCriptoDTO senhas){
        this.tipoExecucao = tipoExecucao;
        this.tempoDeExecucao = senhas.tempoDeExecucao();
        this.data = Datas.agora();
    }
}
