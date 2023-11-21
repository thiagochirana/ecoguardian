package br.com.ecoguardian.services;

import br.com.ecoguardian.models.Denuncia;
import br.com.ecoguardian.models.RegistroDenuncia;
import br.com.ecoguardian.models.Usuario;
import br.com.ecoguardian.models.enums.StatusDenuncia;
import br.com.ecoguardian.models.records.RegistroDenunciaJSON;
import br.com.ecoguardian.repositories.RegistroDenunciaRepository;
import br.com.ecoguardian.utils.Datas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegistroDenunciaService {

    @Autowired
    private RegistroDenunciaRepository registros;

    @Autowired
    private UsuarioService usuarios;


    public RegistroDenuncia abrir(Denuncia denuncia){
        RegistroDenuncia registro = new RegistroDenuncia();
        registro.setDenuncia(denuncia);
        registro.setTitulo("Abertura de Registro da Denuncia n. "+denuncia.getId());
        registro.setDescricao("Abertura de nova denúncia realizadas às "+denuncia.getDataAbertura()+" pelo usuário "+denuncia.getDenunciante().getNome());
        return this.registrar(registro);
    }

    public RegistroDenuncia registrar(RegistroDenuncia registro){
        registro.setDataHoraRegistro(Datas.agora());
        return registros.save(registro);
    }

    public RegistroDenuncia encerrar(RegistroDenuncia registro){
        RegistroDenuncia reg = registrar(registro);
        reg.setStatusAtual(StatusDenuncia.FECHADA);
        reg.setTitulo("Denúncia Encerrada");
        reg.setDataHoraRegistro(Datas.agora());
        reg.setDescricao("Denúncia encerrada às "+reg.getDataHoraRegistro()+" pelo usuario "+reg.getQuemAtualizou().getNome());
        return registrar(reg);
    }

    public List<RegistroDenuncia> listarDaDenuncia(Denuncia denuncia){
        return registros.daDenuncia(denuncia).orElseGet(ArrayList::new);
    }
}
