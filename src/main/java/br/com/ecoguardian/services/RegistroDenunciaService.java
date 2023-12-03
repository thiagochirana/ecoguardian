package br.com.ecoguardian.services;

import br.com.ecoguardian.models.Denuncia;
import br.com.ecoguardian.models.RegistroDenuncia;
import br.com.ecoguardian.models.Usuario;
import br.com.ecoguardian.models.enums.StatusDenuncia;
import br.com.ecoguardian.models.records.RegistroDenunciaJSON;
import br.com.ecoguardian.repositories.DenunciaRepository;
import br.com.ecoguardian.repositories.RegistroDenunciaRepository;
import br.com.ecoguardian.utils.Datas;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class RegistroDenunciaService {

    @Autowired
    private RegistroDenunciaRepository registros;

    @Autowired
    private UsuarioService usuarios;

    @Autowired
    private DenunciaRepository denuncias;

    @Autowired
    private SessaoServiceWrapper sessao;

    public RegistroDenuncia abrir(Denuncia denuncia){
        RegistroDenuncia registro = new RegistroDenuncia();
        registro.setDenuncia(denuncia);
        registro.setStatusAtual(StatusDenuncia.ABERTA);
        registro.setQuemAtualizou(usuarios.obterPeloId(1L));
        registro.setTitulo("Abertura de Registro da Denuncia n. "+denuncia.getId());
        registro.setDescricao("Abertura de nova denúncia realizadas às "+denuncia.dataHoraDeAbertura()+" pelo usuário "+denuncia.getDenunciante().getNome());
        return this.salvarAlteracoes(registro);
    }

    public RegistroDenuncia salvarAlteracoes(RegistroDenunciaJSON json){
        Usuario usuario = usuarios.obterPeloId(json.idUsuario());
        Denuncia denuncia = denuncias.findById(json.denunciaId()).orElseGet(Denuncia::new);
        RegistroDenuncia registro = new RegistroDenuncia(json, usuario, denuncia);
        if (registro.getStatusAtual() == StatusDenuncia.REJEITADA || registro.getStatusAtual() == StatusDenuncia.RESOLVIDA){
            return encerrar(registro);
        }
        if (!usuario.isAdminOuAnalista() || !usuario.temAcessoTotal()){
            StatusDenuncia status;
            if (jaEstaEmAnalise(denuncia)){
                status = StatusDenuncia.EM_ANALISE;
                registro.setStatusAtual(status);
            } else {
                status = StatusDenuncia.AGUARDANDO_ANALISE;
                registro.setStatusAtual(status);
                denuncia.setStatusDenuncia(status);
            }
        }
        return salvarAlteracoes(registro);
    }

    public RegistroDenuncia encerrar(RegistroDenuncia registro){
        RegistroDenuncia reg = salvarAlteracoes(registro);
        RegistroDenuncia novoReg = new RegistroDenuncia();
        novoReg.setStatusAtual(StatusDenuncia.FECHADA);
        novoReg.setTitulo("Denúncia protocolo "+reg.getDenuncia().getProtocolo()+" Encerrada");
        novoReg.setDescricao("Denúncia "+reg.getStatusAtual().getNome()+" às "+reg.dataHoraRegistroFormatada()+" pelo usuario "+sessao.getUsuarioLogado().getNome());
        novoReg.setDenuncia(reg.getDenuncia());
        return salvarAlteracoes(novoReg);
    }

    private RegistroDenuncia salvarAlteracoes(RegistroDenuncia registro){
        registro.setDataHoraRegistro(Datas.agora());
        registro.getDenuncia().setStatusDenuncia(registro.getStatusAtual());
        denuncias.save(registro.getDenuncia());
        return registros.save(registro);
    }

    public List<RegistroDenuncia> listarDaDenuncia(Denuncia denuncia){
        return registros.daDenuncia(denuncia).orElseGet(ArrayList::new);
    }

    public List<RegistroDenuncia> todasAtualizadasPeloAnalista(){
        Usuario analista = sessao.getUsuarioLogado();
        Set<RegistroDenuncia> listaRegistros = registros.atualizadasPeloAnalista(analista).orElseGet(HashSet::new);
        return listaRegistros.stream().toList();
    }

    private boolean jaEstaEmAnalise(Denuncia denuncia){
        for (RegistroDenuncia r : listarDaDenuncia(denuncia)){
            if (r.getStatusAtual() != StatusDenuncia.ABERTA && r.getStatusAtual() != StatusDenuncia.AGUARDANDO_ANALISE){
                return true;
            }
        }
        return false;
    }
}
