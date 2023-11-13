package br.com.ecoguardian.models;

import br.com.ecoguardian.utils.Datas;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class LoginUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    private Long tempoCriptografia;

    private Long tempoDescriptografia;

    private Date dataLogin;

    public LoginUsuario(){}

    public LoginUsuario(Usuario usuario, Long tempoCripto, Long tempoDescripto){
        this.usuario = usuario;
        this.tempoCriptografia = tempoCripto;
        this.tempoDescriptografia = tempoDescripto;
        this.dataLogin = Datas.agora();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
