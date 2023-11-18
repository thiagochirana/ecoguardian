package br.com.ecoguardian.models.records;

import br.com.ecoguardian.models.Municipio;
import br.com.ecoguardian.models.Usuario;

public record DenunciaJSON(
        boolean sigilo,

        String logradouro,

        String bairro,

        Municipio municipio,

        String pontoDeReferencia,

        String titulo,

        String descricao,

        Usuario denunciante,

        String email,

        String telefone,

        String outrasInformacoes,

        String provavelAutorNome,

        String provavelAutorDescricao
) {
}
