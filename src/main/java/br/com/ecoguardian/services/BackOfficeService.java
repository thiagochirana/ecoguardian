package br.com.ecoguardian.services;

import br.com.ecoguardian.models.Arquivo;
import br.com.ecoguardian.repositories.ArquivoRepository;
import br.com.ecoguardian.utils.Log;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BackOfficeService {


    @Autowired
    private ArquivoRepository arquivosRepository;

    Log LOG = new Log(BackOfficeService.class);

    public void salvarArquivos(List<MultipartFile> arquivos){
        try {
            for (MultipartFile arq : arquivos) {
                if (!arq.isEmpty()) {
                    String nomeArquivoOriginal = arq.getOriginalFilename();
                    Path path = Paths.get(nomeArquivoOriginal);
                    String nomeSemExtensao = path.getFileName().toString().replaceFirst("[.][^.]+$", "");
                    String formato = path.getFileName().toString().substring(nomeSemExtensao.length() + 1);
                    Arquivo arquivo = new Arquivo(arq.getBytes(), nomeSemExtensao, formato);
                    arquivosRepository.save(arquivo);
                }
            }
        } catch(Exception e) {
            LOG.erro("Houve um erro ao carregar arquivo", e);
        }
    }

    public List<Arquivo> listarTodos(){
        return arquivosRepository.findAll();
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
}
