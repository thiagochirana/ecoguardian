package br.com.ecoguardian.models.records;

import br.com.ecoguardian.models.Municipio;
import br.com.ecoguardian.models.Usuario;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
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

        String dataOcorrencia,

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
    public DenunciaJSON getJSONparaAnonimo(){
        return new DenunciaJSON(
                true,
                this.logradouro,
                this.numero,
                this.CEP,
                this.bairro,
                this.idIBGE,
                this.latitude,
                this.longitude,
                this.pontoDeReferencia,
                this.titulo,
                this.dataOcorrencia,
                this.descricao,
                this.categoriaId,
                this.subcategoriaId,
               "5",  // Id usuario anônimo
                this.email,
                this.telefone,
                this.outrasInformacoes,
                this.provavelAutorNome,
                this.provavelAutorDescricao
        );
    }
}
