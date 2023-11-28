package br.com.ecoguardian.services;

import br.com.ecoguardian.models.Arquivo;
import br.com.ecoguardian.models.Denuncia;
import br.com.ecoguardian.repositories.ArquivoRepository;
import br.com.ecoguardian.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArquivoService {

    @Autowired
    private ArquivoRepository arquivosRepository;

    Log LOG = new Log(ArquivoService.class);

    public Denuncia salvarArquivosDaDenuncia(Denuncia denuncia, List<MultipartFile> arquivos){
        denuncia.setImagens(salvarArquivos(arquivos));
        return denuncia;
    }

    public Denuncia salvarArquivosTratadosDaDenuncia(Denuncia denuncia, List<Arquivo> arquivos){
        denuncia.setImagens(salvarArquivosTratados(arquivos));
        return denuncia;
    }


    public List<Arquivo> salvarArquivosTratados(List<Arquivo> arquivos){
        return arquivosRepository.saveAll(arquivos);
    }

    public List<Arquivo> salvarArquivos(List<MultipartFile> arquivos){
        try {
            List<Arquivo> arquivosSalvos = new ArrayList<>();
            if (arquivos != null && !arquivos.isEmpty()){
                for (MultipartFile arq : arquivos) {
                    String nomeArquivoOriginal = arq.getOriginalFilename();
                    Path path = Paths.get(nomeArquivoOriginal);
                    String nomeSemExtensao = path.getFileName().toString().replaceFirst("[.][^.]+$", "");
                    String formato = path.getFileName().toString().substring(nomeSemExtensao.length() + 1);
                    Arquivo arquivo = new Arquivo(arq.getBytes(), nomeSemExtensao, formato);
                    arquivosSalvos.add(arquivosRepository.save(arquivo));
                }
            }
            return arquivosSalvos;
        } catch(Exception e) {
            LOG.erro("Houve um erro ao carregar arquivo", e);
            return new ArrayList<>();
        }
    }

    public ResponseEntity<byte[]> obterImagem(Long id){
        Optional<Arquivo> arq = arquivosRepository.findById(id);
        if (arq.isPresent()){
            Arquivo arquivo = arq.get();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + arquivo.getNome()+"."+arquivo.getFormato() + "\"")
                    .body(arquivo.getArquivo());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public List<Arquivo> listarTodos(){
        return arquivosRepository.findAll();
    }

    public List<Arquivo> listarTodosDaDenuncia(Denuncia denuncia){
        List<Arquivo> lista = new ArrayList<>();
        for (Arquivo ar : denuncia.getImagens()){
            lista.add(arquivosRepository.findById(ar.getId()).get());
        }
        return lista;
    }
}
