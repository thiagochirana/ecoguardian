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
        return this.persistirAlteracoes(registro);
    }

    public RegistroDenuncia salvarComentarioAguardandoAnalise(RegistroDenunciaJSON json){
        RegistroDenunciaJSON rdjson = new RegistroDenunciaJSON(
                json.denunciaId(),
                json.titulo(),
                json.descricao(),
                json.idUsuario(),
                StatusDenuncia.AGUARDANDO_ANALISE
        );
        return persistirAlteracoes(rdjson);
    }

    public RegistroDenuncia iniciarAnalise(RegistroDenunciaJSON json){
        RegistroDenunciaJSON rdjson = new RegistroDenunciaJSON(
                json.denunciaId(),
                json.titulo(),
                json.descricao(),
                json.idUsuario(),
                StatusDenuncia.ANALISE_INICIADA
        );
        return persistirAlteracoes(rdjson);
    }

    public RegistroDenuncia analiseResolvida(RegistroDenunciaJSON json){
        RegistroDenunciaJSON rdjson = new RegistroDenunciaJSON(
                json.denunciaId(),
                json.titulo(),
                json.descricao(),
                json.idUsuario(),
                StatusDenuncia.RESOLVIDA
        );
        return persistirAlteracoes(rdjson);
    }

    public RegistroDenuncia rejeitarDenuncia(RegistroDenunciaJSON json){
        RegistroDenunciaJSON rdjson = new RegistroDenunciaJSON(
                json.denunciaId(),
                json.titulo(),
                json.descricao(),
                json.idUsuario(),
                StatusDenuncia.REJEITADA
        );
        return persistirAlteracoes(rdjson);
    }


    public RegistroDenuncia adicionarComentarioJaIniciado(RegistroDenunciaJSON json){
        RegistroDenunciaJSON rdjson = new RegistroDenunciaJSON(
                json.denunciaId(),
                json.titulo(),
                json.descricao(),
                json.idUsuario(),
                StatusDenuncia.EM_ANALISE
        );
        return persistirAlteracoes(rdjson);
    }

    public RegistroDenuncia encerradaPeloUsuario(RegistroDenunciaJSON json){
        RegistroDenunciaJSON rdjson = new RegistroDenunciaJSON(
                json.denunciaId(),
                json.titulo(),
                json.descricao(),
                json.idUsuario(),
                StatusDenuncia.ENCERRADA_PELO_DENUNCIANTE
        );
        return persistirAlteracoes(rdjson);
    }



    public RegistroDenuncia persistirAlteracoes(RegistroDenunciaJSON json){
        Usuario usuario = usuarios.obterPeloId(json.idUsuario());
        Denuncia denuncia = denuncias.findById(json.denunciaId()).orElseGet(Denuncia::new);
        RegistroDenuncia registro = new RegistroDenuncia(json, usuario, denuncia);
        if (registro.getStatusAtual() == StatusDenuncia.REJEITADA || registro.getStatusAtual() == StatusDenuncia.RESOLVIDA){
            return encerrar(registro);
        }
        if (registro.getStatusAtual() == StatusDenuncia.ANALISE_INICIADA){
            return startAnalise(registro);
        }
        if (registro.getStatusAtual() == StatusDenuncia.ENCERRADA_PELO_DENUNCIANTE){
            return encerrar(registro);
        }
        return persistirAlteracoes(registro);
    }

    public RegistroDenuncia startAnalise(RegistroDenuncia registro){
        registro.setTitulo("Análise a denúncia iniciada");
        registro.setQuemAtualizou(sessao.getUsuarioLogado());
        registro.setDescricao("Start da análise às "+registro.dataHoraRegistroFormatada()+" e que será realizada inicialmente pelo analista "+registro.getQuemAtualizou().getNome());
        return persistirAlteracoes(registro);
    }

    public RegistroDenuncia encerrar(RegistroDenuncia registro){
        RegistroDenuncia reg = persistirAlteracoes(registro);
        RegistroDenuncia novoReg = new RegistroDenuncia();
        novoReg.setStatusAtual(StatusDenuncia.FECHADA);
        novoReg.setTitulo("Denúncia protocolo "+reg.getDenuncia().getProtocolo()+" Encerrada");
        novoReg.setDescricao("Denúncia "+reg.getStatusAtual().getNome()+" às "+reg.dataHoraRegistroFormatada()+" pelo usuario "+sessao.getUsuarioLogado().getNome());
        novoReg.setDenuncia(reg.getDenuncia());
        novoReg.setQuemAtualizou(sessao.getUsuarioLogado());
        return persistirAlteracoes(novoReg);
    }

    @Transactional
    public RegistroDenuncia persistirAlteracoes(RegistroDenuncia registro){
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
}
