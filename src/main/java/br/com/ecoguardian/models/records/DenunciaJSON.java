package br.com.ecoguardian.models.records;

import br.com.ecoguardian.models.Municipio;
import br.com.ecoguardian.models.Usuario;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record DenunciaJSON(
        boolean sigilo,

        String logradouro,

        String numero,

        String CEP,

        String bairro,

        String idIBGE,

        String latitude,

        String longitude,

        String pontoDeReferencia,

        String titulo,

        String descricao,

        Long categoriaId,

        Long subcategoriaId,

        String denuncianteId,

        String email,

        String telefone,

        String outrasInformacoes,

        String provavelAutorNome,

        String provavelAutorDescricao
) {
}
